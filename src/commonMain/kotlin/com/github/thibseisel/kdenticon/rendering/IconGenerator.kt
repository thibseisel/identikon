/*
 * Copyright 2017 Thibault Seisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.thibseisel.kdenticon.rendering

import com.github.thibseisel.kdenticon.IdenticonStyle
import com.github.thibseisel.kdenticon.bytesToInt
import com.github.thibseisel.kdenticon.shape.CenterShapes
import com.github.thibseisel.kdenticon.shape.OuterShapes
import com.github.thibseisel.kdenticon.shape.Shape
import com.github.thibseisel.kdenticon.shape.ShapeCategory
import com.github.thibseisel.kdenticon.shape.xy

/**
 * Generates identicons and optionally render them.
 * This class dictates what shapes will be used in the generated icons.
 */
public open class IconGenerator {

    /**
     * The number of cells in each direction of the icons generated by this generator.
     * In each cell is drawn a different shape.
     * The more side-cells an icon has, the more complex it is.
     * Since all icons are 2D graphics, the total number of cells will be `cellCount^2`.
     *
     * The default implementation uses a 4 cells-wide grid.
     */
    public open val cellCount: Int = 4

    /**
     * Called by the generator to determine which color hue must be used for a icon.
     * The hue may be calculated from the provided hash but is not required to,
     * for example if you want all generated icons to share similar colors.
     *
     * The returned value must fit in `[0.0, 1.0]`.
     *
     * The default implementation only uses the 4 first bytes from the hash
     * and divide the resulting integer by `0xFFFFFFFFL`.
     *
     * @param hash array of bytes that can be used as a decision factor to determine the color hue to use
     * @return a color hue to use for the generated icons in range `[0.0, 1.0]`.
     */
    protected open fun computeHue(hash: ByteArray): Float = computeHueInternal(hash)

    /**
     * Default implementation of [computeHue], externalized for testing purposes.
     */
    internal fun computeHueInternal(hash: ByteArray): Float {
        // Convert the resulting integer to Long to avoid negative numbers.
        // As numbers are represented with the 2's complement on the JVM, they may be negative if the 31's bit is 1.
        val actualValue = bytesToInt(hash).toLong() and 0xFFFFFFFFL

        return actualValue.toFloat() / 0xFFFFFFFFL
    }

    /**
     * Called by the generator to retrieve a specified octet from a byte array.
     * Clients may override this to transform the index or the returned byte.
     *
     * The default implementation returns the byte value at the given `index` in `source`
     * as an unsigned byte.
     *
     * @param source The array from which the octet will be retrieved
     * @param index The zero based index of the octet to be returned
     *
     * @return a specific octet from the `source` array, in range `[0, 255]`
     */
    protected open fun getOctet(source: ByteArray, index: Int): Int =
        getOctetInternal(source, index)

    /**
     * Default implementation of [getOctet], externalized for testing purposes.
     * Returns the byte value at th given `index` in `source` as an unsigned byte.
     */
    internal fun getOctetInternal(source: ByteArray, index: Int): Int {
        // Prevent index from being greater than the source array's size
        val rotatedIndex = index % source.size
        val byteAtPosition = source[rotatedIndex]

        return 0xff and byteAtPosition.toInt()
    }

    /**
     * The shape categories to be rendered in icons generated by this renderer.
     */
    protected open val categories: List<ShapeCategory> = DEFAULT_CATEGORIES

    /**
     * Get an enumeration of individual shapes to be rendered in the icon for a specific hash.
     *
     * @param colorTheme A color theme specifying the colors to be used in the icon
     * @param hash The hash for which the shapes will be returned
     */
    protected open fun getShapes(colorTheme: ColorTheme, hash: ByteArray): List<Shape> {
        val shapes = ArrayList<Shape>()
        val usedColorThemeIndexes = ArrayList<Int>()

        for (category in this.categories) {
            var colorThemeIndex = getOctet(hash, category.colorIndex) % colorTheme.count

            // Disallow dark gray + dark color combo
            // and light gray + light gray combo
            if (isDuplicate(usedColorThemeIndexes, colorThemeIndex, 0, 4) ||
                isDuplicate(usedColorThemeIndexes, colorThemeIndex, 2, 3)
            ) {
                colorThemeIndex = 1
            }

            usedColorThemeIndexes.add(colorThemeIndex)

            val startRotationIndex = if (category.rotationIndex == null) 0
            else getOctet(hash, category.rotationIndex)

            val octet = getOctet(hash, category.shapeIndex)
            val index = octet % category.shapes.size

            val shape = category.shapes[index]
            shapes.add(
                Shape(
                    definition = shape,
                    color = colorTheme[colorThemeIndex],
                    positions = category.positions,
                    startRotationIndex = startRotationIndex
                )
            )
        }

        return shapes
    }

    private fun isDuplicate(
        source: List<Int>,
        newValue: Int,
        vararg duplicateValues: Int,
    ): Boolean {
        if (newValue in duplicateValues) {
            for (value in duplicateValues) {
                if (value in source) return true
            }
        }

        return false
    }

    /**
     * Create a quadratic copy of the specified rectangle with a multiple
     * of the cell count as the size.
     *
     * @param rect The rectangle to be normalized
     * @return a rectangle whose size is a multiple of the cell count.
     */
    protected fun normalizedRectangle(rect: Rectangle): Rectangle {
        var size = minOf(rect.width, rect.height)

        // Make size a multiple of the cell count
        size -= (size % cellCount)

        return Rectangle(
            x = rect.x + (rect.width - size) / 2,
            y = rect.y + (rect.height - size) / 2,
            width = size,
            height = size
        )
    }

    /**
     * Called by the generator when the background of the icon should be rendered.
     * This is always called before [renderForeground].
     * Clients may override this to customize the rendered background, or not render it at all.
     *
     * The default implementation renders the background color specified by the provided identicon's color `style`.
     *
     * @param renderer The renderer that will render the icon
     * @param rect The outer bounds of the generated icon
     * @param style The style of the generated icon
     * @param colorTheme A color theme specifying the color to be used in the icon
     * @param hash The hash to be used as basis for the generated icon
     */
    protected open fun renderBackground(
        renderer: Renderer,
        rect: Rectangle,
        style: IdenticonStyle,
        colorTheme: ColorTheme,
        hash: ByteArray,
    ) {
        renderer.setBackground(style.backgroundColor)
    }

    /**
     * Called by the generator when the foreground of the icon should be rendered.
     * This is always called after [renderBackground].
     * Clients may override this to draw additional shapes or completely change the rendering process.
     *
     * The default implementation renders shapes in the icon cells.
     *
     * @param renderer The renderer to be used for rendering the icon on the target surface
     * @param rect The outer bounds of the icon
     * @param style The style of the icon
     * @param colorTheme A color theme specifying the colors to be used in the icon
     * @param hash The hash to be used as basis for the generated icon
     */
    protected open fun renderForeground(
        renderer: Renderer,
        rect: Rectangle,
        style: IdenticonStyle,
        colorTheme: ColorTheme,
        hash: ByteArray,
    ) {
        // Ensure rect is quadratic and a multiple of the cell count
        val normalizedRect = normalizedRectangle(rect)
        val cellSize = normalizedRect.width / this.cellCount

        val shapes = getShapes(colorTheme, hash)
        for (shape in shapes) {

            var rotation = shape.startRotationIndex

            renderer.renderShape(shape.color) {
                for (i in shape.positions.indices) {
                    val position = shape.positions[i]

                    renderer.transform = Transform(
                        normalizedRect.x + position.x * cellSize,
                        normalizedRect.y + position.y * cellSize,
                        cellSize,
                        rotation++ % 4
                    )

                    shape.definition.render(renderer, cellSize, i)
                }
            }
        }
    }

    /**
     * Generates an identicon from a specified hash.
     */
    public fun generate(
        renderer: Renderer,
        rect: Rectangle,
        style: IdenticonStyle,
        hash: ByteArray,
    ) {
        val hue = computeHue(hash)
        check(hue in 0.0f..1.0f) { "Computed hue should be in range [0.0, 1.0]" }

        val colorTheme = ColorTheme(hue, style)
        renderBackground(renderer, rect, style, colorTheme, hash)
        renderForeground(renderer, rect, style, colorTheme, hash)
    }

    private companion object {
        private val DEFAULT_CATEGORIES = listOf(
            // Shapes that are rendered at the outer bounds of the icon
            ShapeCategory(
                colorIndex = 8,
                shapes = OuterShapes.values().toList(),
                shapeIndex = 2,
                rotationIndex = 3,
                positions = listOf(1 xy 0, 2 xy 0, 2 xy 3, 1 xy 3, 0 xy 1, 3 xy 1, 3 xy 2, 0 xy 2)
            ),

            // Shapes that are rendered at the corners of the icons
            ShapeCategory(
                colorIndex = 9,
                shapes = OuterShapes.values().toList(),
                shapeIndex = 4,
                rotationIndex = 5,
                positions = listOf(0 xy 0, 3 xy 0, 3 xy 3, 0 xy 3)
            ),

            // Shapes that are rendered at the center of the icon
            ShapeCategory(
                colorIndex = 10,
                shapes = CenterShapes.values().toList(),
                shapeIndex = 1,
                rotationIndex = null,
                positions = listOf(1 xy 1, 2 xy 1, 2 xy 2, 1 xy 2)
            )
        )
    }
}
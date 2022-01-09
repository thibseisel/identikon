/*
 * Copyright 2022 Thibault Seisel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.thibseisel.identikon.svg

import com.github.thibseisel.identikon.rendering.Color
import com.github.thibseisel.identikon.rendering.PointF
import com.github.thibseisel.identikon.rendering.Renderer
import com.github.thibseisel.identikon.rendering.opacity
import com.github.thibseisel.identikon.rendering.toRgbString

/**
 * A Renderer implementation that renders icons as SVG path instructions.
 *
 * @constructor Creates a new instance of a SvgRenderer.
 * @param width The width of the icon in pixels.
 * @param height The height of the icon in pixels.
 */
internal class SvgRenderer(
    private val width: Int,
    private val height: Int,
) : Renderer() {

    private val pathsByColor = mutableMapOf<Color, SvgPath>()

    private lateinit var path: SvgPath
    private var backgroundColor = Color.hex(0x00000000u)

    override fun renderShape(color: Color, action: () -> Unit) {
        path = pathsByColor.getOrPut(color, ::SvgPath)
        action()
    }

    override fun setBackground(color: Color) {
        backgroundColor = color
    }

    override fun addCircleNoTransform(
        location: PointF,
        diameter: Float,
        counterClockwise: Boolean,
    ) {
        val radius = diameter / 2f
        with(path) {
            moveTo(location.x, location.y + radius)
            arcBy(radius, radius, 0f, diameter, 0f, clockwise = !counterClockwise)
            arcBy(radius, radius, 0f, -diameter, 0f, clockwise = !counterClockwise)
            close()
        }
    }

    override fun addPolygonNoTransform(points: List<PointF>) {
        if (points.isNotEmpty()) {
            with(path) {
                moveTo(points[0].x, points[0].y)
                for (point in points.subList(1, points.size)) {
                    lineTo(point.x, point.y)
                }
                close()
            }
        }
    }

    /**
     * Write SVG instructions with the specified Writer.
     * @param writer The output writer to which the SVG will be written.
     */
    fun save(writer: Appendable) {
        // Add SVG root element tag if requested
        writer.append(
            """
            <svg xmlns="http://www.w3.org/2000/svg"
              width="$width"
              height="$height"
              viewBox="0 0 $width $height"
              preserveAspectRatio="xMidYMid meet">
            """.trimIndent()
        )

        // Draw the background only if it is not transparent
        if (backgroundColor.alpha > 0u) {
            writer.append('\n')
            writer.append(
                """
                |  <rect
                |    fill="${backgroundColor.toRgbString()}"
                |    fill-opacity="${backgroundColor.opacity}"
                |    x="0"
                |    y="0"
                |    width="$width"
                |    height="$height" />
                """.trimMargin()
            )
        }

        // Define each shape as an SVG path
        for ((color, path) in pathsByColor) {
            writer.append('\n')
            writer.append(
                """
                |  <path
                |    fill="${color.toRgbString()}"
                |    d="$path" />
                """.trimMargin()
            )
        }

        // Close the SVG root element if requested
        writer.append('\n')
        writer.append("</svg>")
    }
}

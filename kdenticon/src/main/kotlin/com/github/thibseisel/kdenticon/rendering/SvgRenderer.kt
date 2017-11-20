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

import com.github.thibseisel.kdenticon.Colors
import java.io.Writer
import java.util.*

/**
 * A Renderer implementation that renders icons as SVG path instructions.
 *
 * @constructor Creates a new instance of a SvgRenderer.
 * @param width The width of the icon in pixels.
 * @param height The height of the icon in pixels.
 */
class SvgRenderer(
        private val width: Int,
        private val height: Int
) : Renderer() {

    private val pathsByColor = HashMap<Int, SvgPath>()

    private lateinit var path: SvgPath
    private var backgroundColor = Colors.TRANSPARENT

    override fun renderShape(color: Int, action: Runnable) {
        path = pathsByColor.getOrPut(color, ::SvgPath)
        action.run()
    }

    override fun setBackground(color: Int) {
        backgroundColor = color
    }

    override fun addCircleNoTransform(location: PointF, diameter: Float, counterClockwise: Boolean) {
        path.addCircle(location, diameter, counterClockwise)
    }

    override fun addPolygonNoTransform(points: Array<PointF>) {
        path.addPolygon(points)
    }

    /**
     * Write SVG instructions with the specified Writer.
     * @param writer The output writer to which the SVG will be written.
     * @param partial If `true` an SVG string without the root svg tag will be rendered.
     */
    fun save(writer: Writer, partial: Boolean) {

        // Add SVG root element tag if requested
        if (!partial) {
            writer.write("""
                |<svg xmlns="http://www.w3.org/2000/svg"
                |    width="$width"
                |    height="$height"
                |    viewBox="0 0 $width $height"
                |    preserveAspectRatio="xMidYMid meet">
                """.trimMargin())
        }

        // Draw the background only if it is not transparent
        if (backgroundColor.alpha > 0) {
            val opacity = backgroundColor.alpha / 255f

            writer.write("\n")

            writer.write("""
                |   <rect
                |       fill="${backgroundColor.toRgbString()}"
                |       fill-opacity="$opacity"
                |       x="0"
                |       y="0"
                |       width="$width"
                |       height="$height" />
            """.trimMargin())
        }

        // Define each shape as an SVG path
        for ((color, path) in pathsByColor) {
            writer.write("\n")
            writer.write("""
                |   <path
                |       fill="${color.toRgbString()}"
                |       d="$path" />
                """.trimMargin())
        }

        // Close the SVG root element if requested
        if (!partial) {
            writer.write("\n")
            writer.write("</svg>")
        }
    }
}

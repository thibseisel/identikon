/*
 * Copyright 2021 Thibault Seisel
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

package com.github.thibseisel.kdenticon.svg

import com.github.thibseisel.kdenticon.rendering.Color
import com.github.thibseisel.kdenticon.rendering.PointF
import com.github.thibseisel.kdenticon.rendering.Renderer
import com.github.thibseisel.kdenticon.rendering.opacity
import com.github.thibseisel.kdenticon.rendering.toRgbString

/**
 * A Renderer implementation that renders icons as SVG path instructions.
 *
 * @constructor Creates a new instance of a SvgRenderer.
 * @param width The width of the icon in pixels.
 * @param height The height of the icon in pixels.
 */
public class SvgRenderer(
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
        path.addCircle(location, diameter, counterClockwise)
    }

    override fun addPolygonNoTransform(points: List<PointF>) {
        path.addPolygon(points)
    }

    /**
     * Write SVG instructions with the specified Writer.
     * @param writer The output writer to which the SVG will be written.
     * @param partial If `true` an SVG string without the root svg tag will be rendered.
     */
    public fun save(writer: Appendable, partial: Boolean) {
        // Add SVG root element tag if requested
        if (!partial) {
            writer.append(
                """
                |<svg xmlns="http://www.w3.org/2000/svg"
                |    width="$width"
                |    height="$height"
                |    viewBox="0 0 $width $height"
                |    preserveAspectRatio="xMidYMid meet">
                """.trimMargin()
            )
        }

        // Draw the background only if it is not transparent
        if (backgroundColor.alpha > 0u) {
            writer.append("\n")
            writer.append(
                """
                |   <rect
                |       fill="${backgroundColor.toRgbString()}"
                |       fill-opacity="${backgroundColor.opacity}"
                |       x="0"
                |       y="0"
                |       width="$width"
                |       height="$height" />
                """.trimMargin()
            )
        }

        // Define each shape as an SVG path
        for ((color, path) in pathsByColor) {
            writer.append("\n")
            writer.append(
                """
                |   <path
                |       fill="${color.toRgbString()}"
                |       d="$path" />
                """.trimMargin()
            )
        }

        // Close the SVG root element if requested
        if (!partial) {
            writer.append("\n")
            writer.append("</svg>")
        }
    }
}

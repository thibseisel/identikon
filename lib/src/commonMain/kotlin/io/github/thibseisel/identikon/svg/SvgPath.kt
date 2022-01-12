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

package io.github.thibseisel.identikon.svg

/**
 * Represents a SVG path element.
 */
internal class SvgPath {
    private val pathBuilder = StringBuilder()

    /**
     * Moves the cursor to an absolute position in the SVG grid.
     * Any path should begin with this command.
     */
    fun moveTo(x: Float, y: Float) {
        pathBuilder.append("M$x $y")
    }

    /**
     * Draws a straight line from the current position to the specified absolute coordinates.
     */
    fun lineTo(x: Float, y: Float) {
        pathBuilder.append("L$x $y")
    }

    /**
     * Draws an elliptic arc.
     * That arc ends at coordinates relative to the current cursor position.
     *
     * @param xRadius Radius of the ellipse on the x-axis.
     * @param yRadius Radius of the ellipse on the y-axis.
     * @param xAxisRotation Ellipse rotation angle from the x-axis, expressed in degrees.
     * @param dxEnd Position on the x-axis where the stroke ends, relative to the current position
     * of the cursor.
     * @param dyEnd Position on the y-axis where the stroke ends, relative to the current position
     * of the cursor.
     * @param largeArc Whether the arc should be greater than 180 degrees. If `true`,
     * the arc travels around most of its ellipse. Defaults to `false`.
     * @param clockwise Whether the arc should be drawn clockwise. Defaults to `false`.
     */
    fun arcBy(
        xRadius: Float,
        yRadius: Float,
        xAxisRotation: Float,
        dxEnd: Float,
        dyEnd: Float,
        largeArc: Boolean = false,
        clockwise: Boolean = false,
    ) {
        val sweepFlag = if (clockwise) 1 else 0
        val largeFlag = if (largeArc) 1 else 0
        pathBuilder.append("a$xRadius,$yRadius $xAxisRotation $largeFlag,$sweepFlag $dxEnd,$dyEnd")
    }

    /**
     * Ends the this path by connecting it back to its initial point.
     * An automatic straight line is drawn from the current point to this path's initial point.
     * This path segment may be of zero length.
     */
    fun close() {
        pathBuilder.append("Z")
    }

    override fun toString(): String {
        return pathBuilder.toString()
    }
}

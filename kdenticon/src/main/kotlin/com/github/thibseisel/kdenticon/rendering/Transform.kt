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

/**
 * Defines a mathematical application that translates and rotates a point being rendered.
 */
internal class Transform(
    /** The x-coordinate of the upper left corner of the transformed rectangle. */
    private val x: Int,
    /** The y-coordinate of the upper left corner of the transformed rectangle. */
    private val y: Int,
    /** The size of the transformed rectangle. */
    private val size: Int,
    /** Rotation specified as 0 = 0 rad, 1 = 0.5π rad, 2 = π rad, 3 = 1.5π rad. */
    private val rotation: Int,
) {
    /**
     * Transforms the specified point based on the translation and rotation specification for this Transform.
     *
     * @param x      x-coordinate of the point
     * @param y      y-coordinate of the point
     * @param width  The width of the transformed rectangle.
     *               If greater than 0, this will ensure the returned point is of the upper left corner
     *               of the transformed rectangle
     * @param height The height of the transformed rectangle.
     *               If greater than 0, this will ensure the returned point is of the upper left corner
     *               of the transformed rectangle
     * @return the transformed point
     */
    fun transformPoint(x: Float, y: Float, width: Float = 0f, height: Float = 0f): PointF {
        val right = this.x + size
        val bottom = this.y + size

        return when (rotation) {
            1 -> PointF(right - y - height, this.y + x)
            2 -> PointF(right - x - width, bottom - y - height)
            3 -> PointF(this.x + y, bottom - x - width)
            else -> PointF(this.x + x, this.y + y)
        }
    }

    companion object {
        /**
         * A Transform instance that does not apply any transformation.
         */
        @JvmField
        val EMPTY = Transform(0, 0, 0, 0)
    }
}

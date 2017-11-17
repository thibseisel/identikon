package com.github.thibseisel.kdenticon.rendering

/**
 * Represents the coordinates of a single point in a geometric plane.
 *
 * @param x The X-coordinate of this point
 * @param y The Y-coordinate of this point
 */
data class PointF(val x: Float, val y: Float) {

    override fun toString(): String {
        return String.format("( %1$02f, %2$02f )", x, y)
    }
}
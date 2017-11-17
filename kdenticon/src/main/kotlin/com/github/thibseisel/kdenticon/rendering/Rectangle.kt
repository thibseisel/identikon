package com.github.thibseisel.kdenticon.rendering

/**
 * Represents a rectangle in the geometric plane.
 */
class Rectangle(
        /** The X-coordinate of the left edge of this rectangle. */
        val x: Int,
        /** The Y-coordinate of the top edge of this rectangle. */
        val y: Int,
        /** The width of this rectangle starting from its top-left vertex. */
        val width: Int,
        /** The height of this rectangle starting from its top-left vertex. */
        val height: Int
)
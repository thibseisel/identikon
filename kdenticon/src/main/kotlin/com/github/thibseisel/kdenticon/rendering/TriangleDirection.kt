package com.github.thibseisel.kdenticon.rendering

/**
 * Defines the direction in which the 90° angle of a triangle points to.
 * All rectangles in Kdenticon default shapes are right.
 */
object TriangleDirection {
    /**
     * The 90 degree angle is pointing to South West.
     */
    const val SOUTH_WEST = 0
    /**
     * The 90 degree angle is pointing to North West.
     */
    const val NORTH_WEST = 1
    /**
     * The 90 degree angle is pointing to North East.
     */
    const val NORTH_EAST = 2
    /**
     * The 90 degree angle is pointing to South East.
     */
    const val SOUTH_EAST = 3
}
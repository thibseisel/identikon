package com.github.thibseisel.kdenticon.shape

/**
 * Specifies in which cell a shape will be rendered.
 */
class ShapePosition(
        /**
         * The x-coordinate of the containing cell.
         */
        val x: Int,
        /**
         * The y-coordinate of the containing cell.
         */
        val y: Int) {

    /**
     * The x-coordinate of the containing cell.
     */
    operator fun component1() = x

    /**
     * The y-coordinate of the containing cell.
     */
    operator fun component2() = y

    /**
     * Returns a hash code value for the object.
     * @see Any.hashCode
     */
    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @see Any.equals
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ShapePosition) return false

        return x == other.x && y == other.y
    }

    /**
     * Returns a string representation of this shape position.
     */
    override fun toString() = "{$x, $y}"
}

/**
 * Convenience function to create a new pair of coordinates
 * for the position of a shape in the grid.
 *
 * @receiver The x-coordinate of the containing cell
 * @param y The y-coordinate of the containing cell
 * @return a new ShapePosition instance for the given coordinates
 */
internal infix fun Int.xy(y: Int) = ShapePosition(this, y)
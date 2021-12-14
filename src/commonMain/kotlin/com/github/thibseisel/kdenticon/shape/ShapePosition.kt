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

package com.github.thibseisel.kdenticon.shape

/**
 * Specifies in which cell a shape will be rendered.
 */
public class ShapePosition(
    /**
     * The x-coordinate of the containing cell.
     */
    public val x: Int,
    /**
     * The y-coordinate of the containing cell.
     */
    public val y: Int,
) {

    /**
     * The x-coordinate of the containing cell.
     */
    public operator fun component1(): Int = x

    /**
     * The y-coordinate of the containing cell.
     */
    public operator fun component2(): Int = y

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
    override fun toString(): String = "{$x, $y}"
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

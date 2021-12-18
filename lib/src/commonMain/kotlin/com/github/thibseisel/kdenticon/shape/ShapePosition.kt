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

package com.github.thibseisel.kdenticon.shape

/**
 * Specifies in which cell a shape will be rendered.
 */
public data class ShapePosition(
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

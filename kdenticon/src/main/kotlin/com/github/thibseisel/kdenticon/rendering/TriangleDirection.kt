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
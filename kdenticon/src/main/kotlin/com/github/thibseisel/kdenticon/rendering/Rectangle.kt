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
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
 * Represents a category of shapes that can be rendered in an icon.
 * These instances are not hash-specific.
 */
internal class ShapeCategory(
    /**
     * The index of the hash octet determining the color of shapes in this category.
     */
    val colorIndex: Int,
    /**
     * A list of possible shape definitions for this category.
     */
    val shapes: List<ShapeDefinition>,
    /**
     * The index of the hash octet determining which of the shape definitions
     * will be used for a particular hash.
     */
    val shapeIndex: Int,
    /**
     * The index of the hash octet determining the rotation index of the shape
     * in the first position.
     */
    val rotationIndex: Int?,
    /**
     * The positions in which the shapes of this category will be rendered.
     */
    val positions: List<ShapePosition>,
)

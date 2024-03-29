/*
 * Copyright 2022 Thibault Seisel
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

package io.github.thibseisel.identikon.shape

import io.github.thibseisel.identikon.rendering.Color

/**
 * Represents a shape to be rendered in an icon.
 * These instances are hash-specific and will be rendered in each cell.
 */
internal class Shape(
    /**
     * The shape definition to be used to render the shape.
     */
    val definition: ShapeDefinition,
    /**
     * The fill color of the shape.
     */
    val color: Color,
    /**
     * The rotation index of the icon in the first position.
     */
    val startRotationIndex: Int,
    /**
     * The positions in which the shape will be rendered.
     */
    val positions: List<ShapePosition>,
)

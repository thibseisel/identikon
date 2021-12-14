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
 * Represents a shape to be rendered in an icon.
 * These instances are hash-specific and will be rendered in each cell.
 */
public class Shape(
    /**
     * The shape definition to be used to render the shape.
     */
    public val definition: ShapeDefinition,
    /**
     * The fill color of the shape.
     */
    public val color: Int,
    /**
     * The rotation index of the icon in the first position.
     */
    public val startRotationIndex: Int,
    /**
     * The positions in which the shape will be rendered.
     */
    public val positions: List<ShapePosition>,
)

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

import com.github.thibseisel.kdenticon.rendering.Renderer

/**
 * Defines instructions used to render a shape.
 *
 * Shapes are drawn on the context of their container cell.
 * This means that for all points to be contained in the cell,
 * they must be defined in `[0.0, cell] x [0.0, cell]`.
 */
internal interface ShapeDefinition {

    /**
     * Renders a shape in the specified drawing context.
     * Clients may call any method(s) of [renderer] to define their shape.
     * While it is advised to render only in the bounds of the containing cell,
     * it is not required to do so.
     *
     * @param renderer Renderer used to render the shape
     * @param cell The bounds of the cell in which the shape is rendered
     * @param index The zero-based index of the current shape position
     */
    fun render(renderer: Renderer, cell: Int, index: Int)
}

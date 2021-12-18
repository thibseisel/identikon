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

package com.github.thibseisel.kdenticon.shape.outer

import com.github.thibseisel.kdenticon.rendering.Renderer
import com.github.thibseisel.kdenticon.shape.ShapeDefinition

/**
 * A rhombus that takes half the surface of its containing cell.
 */
internal object RhombusShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        renderer.addRhombus(0f, 0f, cell.toFloat(), cell.toFloat())
    }
}

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

package io.github.thibseisel.identikon.shape.inner

import io.github.thibseisel.identikon.rendering.Renderer
import io.github.thibseisel.identikon.shape.ShapeDefinition

/**
 * A shape made of 4 transparent rhombus reminiscent of a chess board.
 */
internal object ChessShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val fCell = cell.toFloat()
        val m = fCell * 0.25f

        renderer.addRectangle(0f, 0f, fCell, fCell)
        renderer.addRhombus(m, m, fCell - m, fCell - m, true)
    }
}

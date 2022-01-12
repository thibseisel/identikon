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
 * A shape reminiscent of a window made of 4 squares.
 */
internal object WindowShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val tmp = cell * 0.1f
        val inner = when {
            tmp > 1 -> tmp.toInt().toFloat()
            tmp > 0.5f -> 1f
            else -> tmp
        }

        val outer = when {
            cell < 6 -> 1
            cell < 8 -> 2
            else -> cell / 4
        }.toFloat()

        renderer.addRectangle(outer, outer, cell - inner - outer, cell - inner - outer)
    }
}

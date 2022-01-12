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
 * A transparent shape made of 4 rectangles reminiscent of a window.
 */
internal object InverseWindowShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val fCell = cell.toFloat()
        val tmp = fCell * 0.14f
        // For a small icon, use an anti-alias border
        // For a large icon, truncate decimals
        val inner = when {
            cell < 8 -> tmp
            else -> tmp.toInt().toFloat()
        }

        // Use fixed outer border widths in small icons to ensure the border is drawn
        val outer = when {
            cell < 4 -> 1
            cell < 6 -> 2
            else -> (fCell * 0.35f).toInt()
        }.toFloat()

        renderer.addRectangle(0f, 0f, fCell, fCell)
        renderer.addRectangle(
            x = outer,
            y = outer,
            width = cell - outer - inner,
            height = cell - outer - inner,
            invert = true
        )
    }
}

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

import io.github.thibseisel.identikon.rendering.PointF
import io.github.thibseisel.identikon.rendering.Renderer
import io.github.thibseisel.identikon.shape.ShapeDefinition

/**
 * A transparent windmill-like shape over a colored background.
 */
internal object InverseWindmillShape : ShapeDefinition {

    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val fCell = cell.toFloat()
        val k = fCell * 0.42f

        renderer.addPolygon(
            listOf(
                PointF(0f, 0f),
                PointF(fCell, 0f),
                PointF(fCell, fCell - k * 2),
                PointF(fCell - k, fCell),
                PointF(0f, fCell)
            )
        )
    }
}

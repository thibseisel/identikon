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

package com.github.thibseisel.identikon.shape.inner

import com.github.thibseisel.identikon.rendering.PointF
import com.github.thibseisel.identikon.rendering.Renderer
import com.github.thibseisel.identikon.shape.ShapeDefinition

/**
 * Four transparent triangles centered in their respective cell
 * reminiscent of shards.
 */
internal object FourShardsShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val inner = cell * 0.1f
        val outer = inner * 4

        renderer.addRectangle(0f, 0f, cell.toFloat(), cell.toFloat())
        renderer.addPolygon(
            points = listOf(
                PointF(outer, outer),
                PointF(cell - inner, outer),
                PointF(outer + (cell - outer - inner) / 2, cell - inner)
            ),
            invert = true
        )
    }
}

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

package com.github.thibseisel.kdenticon.shape.inner

import com.github.thibseisel.kdenticon.rendering.PointF
import com.github.thibseisel.kdenticon.rendering.Renderer
import com.github.thibseisel.kdenticon.shape.ShapeDefinition

/**
 * A centered transparent rhombus made of 4 colored polygons.
 */
internal object InverseRhombusPartShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val fCell = cell.toFloat()
        renderer.addPolygon(
            arrayOf(
                PointF(0f, 0f),
                PointF(fCell, 0f),
                PointF(fCell, fCell / 2f),
                PointF(fCell / 2f, fCell),
                PointF(0f, fCell)
            )
        )
    }
}

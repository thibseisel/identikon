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
import io.github.thibseisel.identikon.rendering.TriangleDirection
import io.github.thibseisel.identikon.shape.ShapeDefinition

/**
 * A centered rhombus made of 4 isosceles right triangles.
 */
internal object RhombusPartShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val fCell = cell.toFloat()
        renderer.addTriangle(
            x = fCell / 2f,
            y = fCell / 2f,
            width = fCell / 2f,
            height = fCell / 2f,
            direction = TriangleDirection.SOUTH_EAST
        )
    }
}

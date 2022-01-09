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

package io.github.thibseisel.identikon.shape.outer

import io.github.thibseisel.identikon.rendering.Renderer
import io.github.thibseisel.identikon.rendering.TriangleDirection
import io.github.thibseisel.identikon.shape.ShapeDefinition

/**
 * An isosceles right triangle that take half the surface of its containing cell.
 */
internal object LargeTriangleShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        renderer.addTriangle(
            x = 0f,
            y = 0f,
            width = cell.toFloat(),
            height = cell.toFloat(),
            direction = TriangleDirection.SOUTH_WEST
        )
    }
}

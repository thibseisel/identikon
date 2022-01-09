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

package com.github.thibseisel.identikon.shape.outer

import com.github.thibseisel.identikon.rendering.Renderer
import com.github.thibseisel.identikon.shape.ShapeDefinition

/**
 * A circle centered in its containing cell whose radius is 1/3 of the cell width.
 */
internal object CircleShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val m = cell / 6f
        renderer.addCircle(m, m, cell - 2 * m)
    }
}

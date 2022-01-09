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

import com.github.thibseisel.identikon.rendering.Renderer
import com.github.thibseisel.identikon.shape.ShapeDefinition

/**
 * A centered colored square.
 */
internal object SquareShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val s = cell / 3f
        renderer.addRectangle(s, s, cell - s, cell - s)
    }
}

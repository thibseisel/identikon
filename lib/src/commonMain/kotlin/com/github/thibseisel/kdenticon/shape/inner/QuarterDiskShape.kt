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

import com.github.thibseisel.kdenticon.rendering.Renderer
import com.github.thibseisel.kdenticon.shape.ShapeDefinition

/**
 * A circle centered in the 4-cell grid.
 */
internal object QuarterDiskShape : ShapeDefinition {
    override fun render(renderer: Renderer, cell: Int, index: Int) {
        val m = cell * 0.4f
        val s = cell * 1.2f

        if (index != 0) {
            renderer.addCircle(m, m, s)
        }
    }
}

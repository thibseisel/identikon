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

package com.github.thibseisel.kdenticon.shape.outer

import com.github.thibseisel.kdenticon.rendering.Renderer
import com.github.thibseisel.kdenticon.rendering.TriangleDirection
import com.github.thibseisel.kdenticon.shape.ShapeDefinition

/**
 * Default definition of the shapes that are placed around the center of the icon.
 */
internal enum class OuterShapes : ShapeDefinition {

    /**
     * An isosceles right triangle that take half the surface of its containing cell.
     */
    LARGE_TRIANGLE {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            renderer.addTriangle(
                0f,
                0f,
                cell.toFloat(),
                cell.toFloat(),
                TriangleDirection.SOUTH_WEST
            )
        }
    },

    /**
     * A right rectangle whose height is half its width.
     */
    SMALL_TRIANGLE {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            renderer.addTriangle(
                0f,
                cell / 2f,
                cell.toFloat(),
                cell / 2f,
                TriangleDirection.SOUTH_WEST
            )
        }
    },

    /**
     * A rhombus that takes half the surface of its containing cell.
     */
    RHOMBUS {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            renderer.addRhombus(0f, 0f, cell.toFloat(), cell.toFloat())
        }
    },

    /**
     * A circle centered in its containing cell whose radius is 1/3 of the cell width.
     */
    CIRCLE {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val m = cell / 6f
            renderer.addCircle(m, m, cell - 2 * m)
        }
    }
}

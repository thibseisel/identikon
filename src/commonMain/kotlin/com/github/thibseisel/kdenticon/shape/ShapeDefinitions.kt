/*
 * Copyright 2017 Thibault Seisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.thibseisel.kdenticon.shape

import com.github.thibseisel.kdenticon.rendering.PointF
import com.github.thibseisel.kdenticon.rendering.Renderer
import com.github.thibseisel.kdenticon.rendering.TriangleDirection

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

/**
 * Default definition for shapes that are placed in the center of the icon.
 * Those shapes are meant to render a complex shape at the center of the icon by combining 4 cells
 * representing the same shape, but rotated by 90° degrees.
 *
 * Each shape description in this enum describes the combined shape, not the one rendered in one cell.
 */
internal enum class CenterShapes : ShapeDefinition {

    /**
     * A transparent windmill-like shape over a colored background.
     */
    WINDMILL_INVERSE {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val fCell = cell.toFloat()
            val k = fCell * 0.42f

            renderer.addPolygon(
                arrayOf(
                    PointF(0f, 0f),
                    PointF(fCell, 0f),
                    PointF(fCell, fCell - k * 2),
                    PointF(fCell - k, fCell),
                    PointF(0f, fCell)
                )
            )
        }
    },

    /**
     * Four right triangles pointing to the center of the 4-cell grid.
     */
    TRIANGLES {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val fCell = cell.toFloat()
            val w = fCell * 0.5f
            val h = fCell * 0.8f
            renderer.addTriangle(fCell - w, 0f, w, h, TriangleDirection.NORTH_EAST)
        }
    },

    /**
     * A centered colored square.
     */
    SQUARE {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val s = cell / 3f
            renderer.addRectangle(s, s, cell - s, cell - s)
        }
    },

    /**
     * A shape reminiscent of a window made of 4 squares.
     */
    WINDOW {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val tmp = cell * 0.1f
            val inner = when {
                tmp > 1 -> tmp.toInt().toFloat()
                tmp > 0.5f -> 1f
                else -> tmp
            }

            val outer = when {
                cell < 6 -> 1
                cell < 8 -> 2
                else -> cell / 4
            }.toFloat()

            renderer.addRectangle(outer, outer, cell - inner - outer, cell - inner - outer)
        }
    },

    /**
     * Four circles centered in the 4-cell grid.
     */
    CIRCLES {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val m = (cell * 0.15f).toInt()
            val s = (cell * 0.5f).toInt()
            val pos = (cell - s - m).toFloat()
            renderer.addCircle(pos, pos, s.toFloat())
        }
    },

    /**
     * Four transparent triangles centered in their respective cell
     * reminiscent of shards.
     */
    SHARDS {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val inner = cell * 0.1f
            val outer = inner * 4

            renderer.addRectangle(0f, 0f, cell.toFloat(), cell.toFloat())
            renderer.addPolygon(
                arrayOf(
                    PointF(outer, outer),
                    PointF(cell - inner, outer),
                    PointF(outer + (cell - outer - inner) / 2, cell - inner)
                ), true
            )
        }
    },

    /**
     * A transparent cross rendered over a colored background.
     */
    CROSS {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val fCell = cell.toFloat()
            renderer.addPolygon(
                arrayOf(
                    PointF(0f, 0f),
                    PointF(fCell, 0f),
                    PointF(fCell, fCell * 0.7f),
                    PointF(fCell * 0.4f, fCell * 0.4f),
                    PointF(fCell * 0.7f, fCell),
                    PointF(0f, fCell)
                )
            )
        }
    },

    /**
     * A centered rhombus made of 4 isosceles right triangles.
     */
    RHOMBUS {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val fCell = cell.toFloat()
            renderer.addTriangle(
                fCell / 2f, fCell / 2f,
                fCell / 2f, fCell / 2f, TriangleDirection.SOUTH_EAST
            )
        }
    },

    /**
     * A centered transparent rhombus made of 4 colored polygons.
     */
    RHOMBUS_INVERSE {
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
    },

    /**
     * A transparent shape made of 4 rectangles reminiscent of a window.
     */
    WINDOW_INVERSE {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val fCell = cell.toFloat()
            val tmp = fCell * 0.14f
            // For a small icon, use an anti-alias border
            // For a large icon, truncate decimals
            val inner = if (cell < 8) tmp else tmp.toInt().toFloat()

            // Use fixed outer border widths in small icons to ensure the border is drawn
            val outer = when {
                cell < 4 -> 1
                cell < 6 -> 2
                else -> (fCell * 0.35f).toInt()
            }.toFloat()

            renderer.addRectangle(0f, 0f, fCell, fCell)
            renderer.addRectangle(
                outer, outer,
                cell - outer - inner, cell - outer - inner, true
            )
        }
    },

    /**
     * Four transparent circles centered in the 4-cell grid over a colored background.
     */
    CIRCLES_INVERSE {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val fCell = cell.toFloat()
            val inner = fCell * 0.12f
            val outer = inner * 3f

            renderer.addRectangle(0f, 0f, fCell, fCell)
            renderer.addCircle(outer, outer, fCell - inner - outer, true)
        }
    },

    /**
     * A shape made of 4 transparent rhombus reminiscent of a chess board.
     */
    CHESS {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val fCell = cell.toFloat()
            val m = fCell * 0.25f

            renderer.addRectangle(0f, 0f, fCell, fCell)
            renderer.addRhombus(m, m, fCell - m, fCell - m, true)
        }
    },

    /**
     * A circle centered in the 4-cell grid.
     */
    CIRCLE {
        override fun render(renderer: Renderer, cell: Int, index: Int) {
            val m = cell * 0.4f
            val s = cell * 1.2f

            if (index != 0) {
                renderer.addCircle(m, m, s)
            }
        }
    }
}
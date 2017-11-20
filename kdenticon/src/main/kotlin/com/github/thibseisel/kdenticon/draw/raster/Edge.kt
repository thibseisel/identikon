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

package com.github.thibseisel.kdenticon.draw.raster

import com.github.thibseisel.kdenticon.rendering.PointF

/**
 * Specifies an edge of a polygon that is being rendered.
 */
internal class Edge(
        val polygonId: Int,
        val from: PointF,
        val to: PointF,
        val color: Int) {

    override fun toString() = "$polygonId: $from; $to"

    fun intersection(y: Float): Float {
        val dx = (to.x - from.x) * (from.y - y) / (from.y - to.y)
        return from.x + dx
    }

}
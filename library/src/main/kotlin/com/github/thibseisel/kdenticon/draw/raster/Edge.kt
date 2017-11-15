package com.github.thibseisel.kdenticon.draw.raster

import com.github.thibseisel.javadenticon.rendering.PointF

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
package com.github.thibseisel.kdenticon.draw

import com.github.thibseisel.javadenticon.rendering.PointF

internal class Path {

    private val polygons = ArrayList<Array<PointF>>(10)

    fun addPolygon(points: Array<PointF>) {
        polygons.add(points)
    }

    fun addCircle(center: PointF, radius: Float, clockwise: Boolean) {
        TODO()
    }

    fun polygons(): Iterable<Array<PointF>> = polygons
}
package com.github.thibseisel.javadenticon.draw

import com.github.thibseisel.javadenticon.draw.raster.EdgeTable
import com.github.thibseisel.javadenticon.rendering.PointF
import com.github.thibseisel.javadenticon.rendering.colorOf

internal class Bitmap(
        val width: Int,
        val height: Int
) {

    private val edges = EdgeTable(height)
    var backgroundColor: Int = colorOf(255, 255, 255, 255)

    fun fillPolygonCore(id: Int, color: Int, points: Array<PointF>) {
        for (i in points.indices) {

        }
    }
}
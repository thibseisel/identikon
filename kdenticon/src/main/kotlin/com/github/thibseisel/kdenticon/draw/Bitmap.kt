package com.github.thibseisel.kdenticon.draw

import com.github.thibseisel.kdenticon.draw.raster.EdgeTable
import com.github.thibseisel.kdenticon.rendering.PointF
import com.github.thibseisel.kdenticon.rendering.colorOf

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
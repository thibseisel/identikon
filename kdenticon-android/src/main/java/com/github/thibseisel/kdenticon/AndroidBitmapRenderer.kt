package com.github.thibseisel.kdenticon

import android.graphics.*
import com.github.thibseisel.javadenticon.rendering.PointF
import com.github.thibseisel.javadenticon.rendering.Renderer

/**
 * Renders icons onto a Android bitmap using the framework's Canvas.
 */
class AndroidBitmapRenderer(bitmap: Bitmap) : Renderer() {

    private val canvas = Canvas(bitmap)
    private val paint = Paint()
    private val path = Path()

    override fun addPolygonNoTransform(points: Array<PointF>) {
        path.reset()
        path.moveTo(points[0].x, points[0].y)
        for (i in 1 until points.size) {
            path.lineTo(points[i].x, points[i].y)
        }

        path.close()
        canvas.drawPath(path, paint)
    }

    override fun addCircleNoTransform(location: PointF, diameter: Float, counterClockwise: Boolean) {
        val radius = diameter / 2f
        val cx = location.x + radius
        val cy = location.y + radius
        canvas.drawCircle(cx, cy, radius, paint)
    }

    override fun setBackground(color: Int) {
        canvas.drawColor(color)
    }

    override fun renderShape(color: Int, action: Runnable) {
        paint.color = color
        paint.style = Paint.Style.FILL
        action.run()
    }
}
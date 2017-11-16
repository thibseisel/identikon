package com.github.thibseisel.kdenticon.android

import android.graphics.*
import com.github.thibseisel.kdenticon.rendering.PointF
import com.github.thibseisel.kdenticon.rendering.Renderer

/**
 * Renders an icon onto a Android [Bitmap] using the framework's Canvas.
 * This renderer implementation make it easy to integrate Kdenticon with Android.
 *
 * The resulting bitmap could then be directly displayed in an ImageView or saved as a PNG file
 * using [Bitmap.compress].
 *
 * @constructor Creates a new instance of a renderer that draw the icon on the provided bitmap.
 * @param bitmap The bitmap on which the icon should be drawn.
 */
class AndroidBitmapRenderer(bitmap: Bitmap) : Renderer() {

    private val canvas = Canvas(bitmap)
    private val path = Path()
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun addPolygonNoTransform(points: Array<PointF>) {
        if (points.isNotEmpty()) {

            path.moveTo(points[0].x, points[0].y)
            points.forEach { path.lineTo(it.x, it.y) }

            // Close the shape by linking the last point to the first one
            path.lineTo(points[0].x, points[0].y)
        }

    }

    override fun addCircleNoTransform(location: PointF, diameter: Float, counterClockwise: Boolean) {
        val radius = diameter / 2f
        val cx = location.x + radius
        val cy = location.y + radius
        val direction = if (counterClockwise) Path.Direction.CCW else Path.Direction.CW

        path.addCircle(cx, cy, radius, direction)
    }

    override fun setBackground(color: Int) {
        canvas.drawColor(color)
    }

    override fun renderShape(color: Int, action: Runnable) {
        path.reset()
        action.run()

        paint.color = color
        canvas.drawPath(path, paint)
    }
}
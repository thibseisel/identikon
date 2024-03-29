/*
 * Copyright 2022 Thibault Seisel
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

package io.github.thibseisel.identikon.android

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import io.github.thibseisel.identikon.rendering.Color
import io.github.thibseisel.identikon.rendering.PointF
import io.github.thibseisel.identikon.rendering.Renderer

/**
 * Renders an icon onto a Android [Bitmap] using the framework's Canvas.
 * This renderer implementation make it easy to integrate identikon with Android.
 *
 * The resulting bitmap could then be directly displayed in an ImageView or saved as a PNG file
 * using [Bitmap.compress].
 *
 * @constructor Creates a new instance of a renderer that draw the icon on the provided bitmap.
 * @param bitmap The bitmap on which the icon should be drawn.
 */
internal class AndroidBitmapRenderer(bitmap: Bitmap) : Renderer() {
    private val canvas = Canvas(bitmap)
    private val path = Path()
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    override fun addPolygonNoTransform(points: List<PointF>) {
        if (points.isNotEmpty()) {
            path.moveTo(points[0].x, points[0].y)
            points.forEach { path.lineTo(it.x, it.y) }

            // Close the shape by linking the last point to the first one
            path.lineTo(points[0].x, points[0].y)
        }
    }

    override fun addCircleNoTransform(
        location: PointF,
        diameter: Float,
        counterClockwise: Boolean,
    ) {
        val radius = diameter / 2f
        val cx = location.x + radius
        val cy = location.y + radius
        val direction = if (counterClockwise) Path.Direction.CCW else Path.Direction.CW

        path.addCircle(cx, cy, radius, direction)
    }

    override fun setBackground(color: Color) {
        canvas.drawColor(color.toArgbInt())
    }

    override fun renderShape(color: Color, action: () -> Unit) {
        path.reset()
        action()

        paint.color = color.toArgbInt()
        canvas.drawPath(path, paint)
    }
}

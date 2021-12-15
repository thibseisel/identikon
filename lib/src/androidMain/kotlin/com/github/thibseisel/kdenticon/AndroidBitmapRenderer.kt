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

package com.github.thibseisel.kdenticon

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
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
public class AndroidBitmapRenderer(bitmap: Bitmap) : Renderer() {
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

    override fun setBackground(color: Int) {
        canvas.drawColor(color)
    }

    override fun renderShape(color: Int, action: () -> Unit) {
        path.reset()
        action()

        paint.color = color
        canvas.drawPath(path, paint)
    }
}

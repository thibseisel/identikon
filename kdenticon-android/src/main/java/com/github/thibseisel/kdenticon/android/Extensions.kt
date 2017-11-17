@file:JvmName("KdenticonAndroid")

package com.github.thibseisel.kdenticon.android

import android.graphics.Bitmap
import com.github.thibseisel.kdenticon.Identicon

/**
 * Utility functions specific to the Kdenticon-Android extension.
 */

/**
 * Renders an Identicon onto a given bitmap using AndroidBitmapRenderer.
 *
 * @receiver Icon to draw onto the provided bitmap
 * @param bitmap The bitmap onto the icon should be drawn
 */
@Suppress("unused")
@JvmName("drawIconToBitmap")
fun Identicon.drawToBitmap(bitmap: Bitmap) {
    val renderer = AndroidBitmapRenderer(bitmap)
    draw(renderer, getIconBounds())
}
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
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

@file:JvmName("Color")

package com.github.thibseisel.kdenticon.rendering

import kotlin.math.roundToInt

/**
 * Create the representation of a color in the sRGB color space as a 32-bits integer.
 *
 * Each channel value is expected to fit in `[0, 255]`.
 * If it doesn't, any extra bit other than the four first ones are set to 0.
 *
 * @param alpha The value of alpha channel, must be in `[0, 255]`
 * @param red The value of the red channel, must be in `[0, 255]`
 * @param green The value of the green channel, must be in `[0, 255]`
 * @param blue The value of the blue channel, must be in `[0, 255]`
 */
@JvmName("fromArgb")
internal fun colorOf(alpha: Int, red: Int, green: Int, blue: Int): Int =
    (alpha and 0xff shl 24) or
            (red and 0xff shl 16) or
            (green and 0xff shl 8) or
            (blue and 0xff)

/**
 * The value for the alpha channel for the color represented by this integer.
 * @receiver a 32-bits integer representing a color in the sRGB space.
 */
internal val Int.alpha: Int get() = (this ushr 24)

/**
 * The value for the red channel for the color represented by this integer.
 * @receiver a 32-bits integer representing a color in the sRGB space.
 */
internal val Int.red: Int get() = (this shr 16) and 0xff

/**
 * The value for the green channel for the color represented by this integer.
 * @receiver a 32-bits integer representing a color in the sRGB space.
 */
internal val Int.green: Int get() = (this shr 8) and 0xff

/**
 * The value for the blue channel for the color represented by this integer.
 * @receiver a 32-bits integer representing a color in the sRGB space.
 */
internal val Int.blue: Int get() = (this) and 0xff

private fun hueToRgb(m1: Float, m2: Float, h: Float): Int {
    val hh = when {
        h < 0 -> h + 6f
        h > 6 -> h - 6f
        else -> h
    }

    return (255 * when {
        hh < 1f -> m1 + (m2 - m1) * hh
        hh < 3f -> m2
        hh < 4f -> m1 + (m2 - m1) * (4f - hh)
        else -> m1
    }).roundToInt()
}

/**
 * Convert a color from the HSL color space to a sRGB color encoded as an integer.
 *
 * @param hue Hue normalized in the range `[0, 1]`.
 *         0.0 is red, 1/3 is green, 2/3 is blue, 1.0 is also red.
 * @param saturation Saturation in the range `[0, 1]`,
 *         expressed in percent where 0% is achromatic and 100% is full color.
 * @param lightness Lightness in the range `[0, 1]`
 *         expressed in percent where 0% is black and 100% is white.
 * @return an ARGB-encoded color integer
 */
@JvmName("fromHsl")
internal fun colorFromHsl(hue: Float, saturation: Float, lightness: Float): Int {
    require(hue in 0.0f..1.0f) { "Hue should be in [0, 1]" }
    require(saturation in 0.0f..1.0f) { "Saturation should be in [0, 1]" }
    require(lightness in 0.0f..1.0f) { "Lightness should be in [0, 1]" }

    return if (saturation == 0f) {
        // No saturation: this is a shape of grey
        val value = (lightness * 255).roundToInt()
        colorOf(255, value, value, value)
    } else {
        // Calculate hue values
        val m2 = if (lightness <= 0.5f) lightness * (saturation + 1f)
        else lightness + saturation - lightness * saturation

        val m1 = lightness * 2f - m2
        colorOf(
            255,
            hueToRgb(m1, m2, hue * 6 + 2),
            hueToRgb(m1, m2, hue * 6),
            hueToRgb(m1, m2, hue * 6 - 2)
        )
    }
}

/**
 * Blends this color with another color using the over bending operation.
 *
 * @receiver a 32-bits integer representing a color in the sRGB space.
 * @param background The color of the background on which this color should be blended over.
 */
internal fun Int.blendOver(background: Int): Int {
    val foreAlpha = this.alpha

    if (foreAlpha < 1) {
        // Foreground is fully transparent, no blending
        return background
    } else if (foreAlpha > 254 || background.alpha < 1) {
        // Foreground is opaque or background is fully transparent, no blending
        return this
    }

    // Source: https://en.wikipedia.org/wiki/Alpha_compositing#Description
    val forePA = foreAlpha * 255
    val backPA = background.alpha * (255 - foreAlpha)
    val alpha = (forePA + backPA)

    val b = ((forePA * this.blue + backPA * background.blue) / alpha)
    val g = ((forePA * this.green + backPA * background.green) / alpha)
    val r = ((forePA * this.red + backPA * background.red) / alpha)
    val a = (alpha / 255)

    return colorOf(a, r, g, b)
}

private val lightnessCompensations = floatArrayOf(0.55f, 0.5f, 0.5f, 0.46f, 0.6f, 0.55f, 0.55f)

/**
 * Create the representation of a color from the HSL color parameters
 * and compensate the lightness for hues that appear to be darker than others.
 *
 * @param hue Hue in the range [0, 1]
 * @param saturation Saturation in the range [0, 1]
 * @param lightness Lightness in the range [0, 1]
 * @return an ARGB-encoded color integer
 */
@JvmName("fromHslCompensated")
internal fun colorFromHslCompensated(hue: Float, saturation: Float, lightness: Float): Int {
    require(hue in 0f..1f) { "Hue should be in [0, 1]" }

    val lightnessCompensation = lightnessCompensations[(hue * 6 + 0.5f).toInt()]

    // Adjust the input lightness relative to the compensation
    val newLightness = when {
        lightness < 0.5f -> lightness * lightnessCompensation * 2f
        else -> lightnessCompensation + (lightness - 0.5f) * (1 - lightnessCompensation) * 2f
    }

    return colorFromHsl(hue, saturation, newLightness)
}

/**
 * Produces a String representation of this color.
 *
 * @receiver an integer encoding a color in the sRGB color space.
 * @return a string representation of this color formatted as `#AARRGGBB`.
 */
internal fun Int.toColorString() = '#' + String.format("%08x", this)

/**
 * Produces a String representation of this color, ignoring alpha channel.
 *
 * @receiver an integer encoding a color in the sRGB color space.
 * @return a string representation of this color formatted as `#RRGGBB`.
 */
internal fun Int.toRgbString() = "#" + String.format("%06x", this and 0xFFFFFF)

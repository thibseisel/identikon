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

package com.github.thibseisel.kdenticon.rendering

import kotlin.jvm.JvmInline
import kotlin.math.roundToInt

/**
 * Abstracts away the machine representation of a color.
 */
@JvmInline
public value class Color private constructor(
    private val rgba: UInt,
) {
    /**
     * Intensity of the red component of this color, expressed as a positive integer in `[0, 255]`.
     */
    public val red: UInt
        get() = (rgba shr 24) and 0xffu

    /**
     * Intensity of the green component of this color, expressed as a positive integer in `[0, 255]`.
     */
    public val green: UInt
        get() = (rgba shr 16) and 0xffu

    /**
     * Intensity of the blue component of this color, expressed as a positive integer in `[0, 255]`.
     */
    public val blue: UInt
        get() = (rgba shr 8) and 0xffu

    /**
     * Opacity of this color, expressed as a positive integer in `[0, 255]`.
     * `0` is fully transparent while `255` is fully opaque.
     */
    public val alpha: UInt
        get() = rgba and 0xffu

    /**
     * Returns a 32-bits representation of this color.
     */
    public fun toRgbaInt(): UInt = rgba

    override fun toString(): String {
        return "#" + rgba.toString(16).padStart(8, '0')
    }

    public companion object Factory {
        /**
         * Defines a color as a 32-bits integer where [red], [green], [blue] and [alpha] components
         * are each encoded as a 8-bits integer, in this order.
         *
         * You may use the hexadecimal notation for readability:
         * `0xFF0000FFu` is a plain red, while `0x7F7F7FFFu` is a medium grey.
         */
        public fun hex(rgba: UInt): Color = Color(rgba)

        /**
         * Defines a color from each of its 4 RGBA components.
         * Each component should be a positive integer in `[0, 255]`.
         */
        public fun rgba(red: UInt, green: UInt, blue: UInt, alpha: UInt = 0xffu): Color {
            checkRgbRange("red", red)
            checkRgbRange("green", green)
            checkRgbRange("blue", blue)
            checkRgbRange("alpha", alpha)

            val encoded = (red shl 24) or (green shl 16) or (blue shl 8) or alpha
            return Color(encoded)
        }

        private fun checkRgbRange(name: String, value: UInt) {
            require(value in 0x00u..0xffu) {
                "$name should be in [0, 255], was $value"
            }
        }

        /**
         * Defines a color using the HSL color space.
         * @param hue Hue normalized in the range `[0, 1]`. `0.0` is red, `0.33` is green,
         * `0.66` is blue, and `1.0` is also red.
         * @param saturation Saturation expressed in percent and in the range `[0, 1]` where
         * 0% is achromatic and 100% is full color.
         * @param lightness Lightness expressed in percents and in the range `[0, 1]` where
         * 0% is black and 100% is white.
         */
        public fun hsl(hue: Float, saturation: Float, lightness: Float): Color {
            checkHslRange("hue", hue)
            checkHslRange("saturation", saturation)
            checkHslRange("lightness", lightness)

            if (saturation == 0f) {
                // No saturation: it is a shade of grey
                val light = (lightness * 255).roundToInt().toUInt()
                return rgba(light, light, light)
            }
            val m2 = when {
                lightness <= 0.5f -> lightness * (saturation + 1f)
                else -> lightness + saturation - lightness * saturation
            }
            val m1 = lightness * 2f - m2
            return rgba(
                red = hueToRgb(m1, m2, hue * 6 + 2),
                green = hueToRgb(m1, m2, hue * 6),
                blue = hueToRgb(m1, m2, hue * 6 - 2)
            )
        }

        private fun hueToRgb(m1: Float, m2: Float, h: Float): UInt {
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
            }).roundToInt().toUInt()
        }

        private fun checkHslRange(componentName: String, value: Float) {
            require(value in 0.0f..1.0f) {
                "$componentName should be in [0.0, 1.0]"
            }
        }
    }
}

/**
 * Factor whose value is in `[0.0, 1.0]` describing how opaque this color is.
 * `0.0` means "completely transparent" while `1.0` is "completely opaque".
 */
public val Color.opacity: Float
    get() = alpha.toInt() / 255f

/**
 * Produces a String representation of this color, ignoring alpha channel.
 * RGB colors have the following format: `#RRGGBB`.
 */
public fun Color.toRgbString(): String {
    val rgb = (red shl 16) or (green shl 8) or blue
    return "#" + rgb.toString(16).padStart(6, '0')
}

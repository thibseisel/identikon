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

package io.github.thibseisel.identikon.rendering

import io.github.thibseisel.identikon.Identicon
import io.github.thibseisel.identikon.IdenticonStyle

/**
 * Specifies the colors to be used in an [Identicon].
 * A color theme is composed of 5 colors: one color with lighter and darker variants,
 * a light shade of gray and a dark shade of gray.
 *
 * @constructor Create a new ColorTheme based on the provided color hue and the given style.
 *
 * @param hue The color hue that will be used to calculate the base color and its variants,
 * must be in `[0.0, 1.0]`.
 * @param style The style of the icon that will be associated to this theme.
 */
internal class ColorTheme(hue: Float, style: IdenticonStyle) {
    init {
        require(hue in 0.0f..1.0f) { "Hue should be in [0.0, 1.0]" }
    }

    /**
     * The base color to use for this theme.
     */
    val color: Color = Color.hsl(
        hue,
        saturation = style.saturation,
        lightness = compensateLightness(
            lightness = (style.colorLightness.start + style.colorLightness.endInclusive) / 2,
            hue
        )
    )

    /**
     * The light color variant for this theme.
     */
    val lightColor: Color = Color.hsl(
        hue,
        saturation = style.saturation,
        lightness = compensateLightness(style.colorLightness.start, hue)
    )

    /**
     * The dark color variant for this theme.
     */
    val darkColor: Color = Color.hsl(
        hue,
        saturation = style.saturation,
        lightness = compensateLightness(style.colorLightness.endInclusive, hue)
    )

    /**
     * The light shade of gray for this theme.
     */
    val lightGray: Color = Color.hsl(0f, 0f, style.grayScaleLightness.endInclusive)

    /**
     * The dark shade of gray for this theme.
     */
    val darkGray: Color = Color.hsl(0f, 0f, style.grayScaleLightness.start)

    /**
     * The total number of colors that this theme features.
     * The default value is `5`.
     */
    val count: Int get() = 5

    operator fun get(index: Int): Color = when (index) {
        0 -> darkGray
        1 -> color
        2 -> lightGray
        3 -> lightColor
        4 -> darkColor
        else -> throw IndexOutOfBoundsException()
    }
}

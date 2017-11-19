package com.github.thibseisel.kdenticon.rendering

import com.github.thibseisel.kdenticon.Identicon
import com.github.thibseisel.kdenticon.IdenticonStyle

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
@Suppress("MemberVisibilityCanPrivate")
class ColorTheme(hue: Float, style: IdenticonStyle) {

    init {
        require(hue in 0.0f .. 1.0f) { "Hue should be in [0.0, 1.0]" }
    }

    /**
     * The base color to use for this theme.
     */
    val color: Int = colorFromHslCompensated(hue, style.saturation,
            (style.colorLightness.start + style.colorLightness.endInclusive) / 2)

    /**
     * The light color variant for this theme.
     */
    val lightColor: Int = colorFromHslCompensated(hue, style.saturation, style.colorLightness.endInclusive)

    /**
     * The dark color variant for this theme.
     */
    val darkColor: Int = colorFromHslCompensated(hue, style.saturation, style.colorLightness.endInclusive)

    /**
     * The light shade of gray for this theme.
     */
    val lightGray: Int = colorFromHsl(0f, 0f, style.grayScaleLightness.endInclusive)

    /**
     * The dark shade of gray for this theme.
     */
    val darkGray: Int = colorFromHsl(0f, 0f, style.grayScaleLightness.start)

    /**
     * The total number of colors that this theme features.
     * The default value is `5`.
     */
    val count: Int get() = 5

    operator fun get(index: Int) = when (index) {
        0 -> darkGray
        1 -> color
        2 -> lightGray
        3 -> lightColor
        4 -> darkColor
        else -> throw IndexOutOfBoundsException()
    }
}
package com.github.thibseisel.kdenticon

import com.github.thibseisel.kdenticon.IdenticonStyle.Companion.DEFAULT_COLOR_LIGHTNESS
import com.github.thibseisel.kdenticon.IdenticonStyle.Companion.DEFAULT_GRAY_SCALE_LIGHTNESS
import com.github.thibseisel.kdenticon.IdenticonStyle.Companion.DEFAULT_PADDING
import com.github.thibseisel.kdenticon.IdenticonStyle.Companion.DEFAULT_SATURATION

/**
 * Specifies the color style of an identicon.
 */
class IdenticonStyle(

        /**
         * The background color of the icon.
         * Set to `0` (`0x00000000`) to remove the background.
         *
         * The default background color is white.
         */
        val backgroundColor: Int = Colors.WHITE,

        /**
         * the padding between the edge of the image and the bounds of the rendered icon.
         * Values are expressed in percent and are expected to fit in the range `[0.0, 0.4]`.
         *
         * This defaults to [DEFAULT_PADDING].
         */
        val padding: Float = DEFAULT_PADDING,

        /**
         * The saturation of the icon in the range `[0.0f, 1.0f]`.
         * This indicates how "intense" the shape colors for this icon should be.
         *
         * This defaults to [DEFAULT_SATURATION].
         */
        val saturation: Float = DEFAULT_SATURATION,

        /**
         * Defines the range of possible values for the picked color's lightness.
         * Range bounds are expected to fit in `[0.0f, 1.0f]`.
         *
         * This defaults to [DEFAULT_COLOR_LIGHTNESS].
         */
        val colorLightness: ClosedFloatingPointRange<Float> = DEFAULT_COLOR_LIGHTNESS,

        /**
         * Defines the range of possible values for the lightness of shades of gray.
         * Range bounds are expected to fit in `[0.0f, 1.0f]`.
         *
         * This defaults to [DEFAULT_GRAY_SCALE_LIGHTNESS].
         */
        val grayScaleLightness: ClosedFloatingPointRange<Float> = DEFAULT_GRAY_SCALE_LIGHTNESS) {

    init {
        // Check invariants
        require(padding in 0.0f .. 0.4f) { "Padding values should be in [0.0, 0.4]" }
        require(saturation in 0.0f .. 1.0f) { "Saturation values should be [0.0, 1.0]" }
        require(colorLightness.start in 0.0f .. 1.0f) { "Incorrect minimum colorLightness" }
        require(colorLightness.endInclusive in 0.0f .. 1.0f) { "Incorrect maximum colorLightness" }
        require(grayScaleLightness.start in 0.0f .. 1.0f) { "Incorrect minimum grayScaleLightness" }
        require(grayScaleLightness.endInclusive in 0.0f .. 1.0f) {
            "Incorrect maximum grayScaleLightness"
        }
    }

    companion object {
        /**
         * The default style used for all icons. Icons using this style have :
         *
         * - a white background
         * - a padding of 8% of the total size
         * - a "normal" saturation of `0.5`
         * - color lightness picked in `[0.4, 0.8]`
         * - gray scale lightness picked in `[0.3, 0.9]`.
         */
        @JvmField val DEFAULT_STYLE = IdenticonStyle()
        @JvmField val DEFAULT_COLOR_LIGHTNESS = (0.4f..0.8f)
        @JvmField val DEFAULT_GRAY_SCALE_LIGHTNESS = (0.3f..0.9f)

        const val DEFAULT_PADDING = 0.08f
        const val DEFAULT_SATURATION = 0.5f

        /**
         * Helper function for Java to create ranges of floating point numbers.
         */
        @Suppress("unused")
        @JvmStatic fun createRange(start: Float, endInclusive: Float) = start .. endInclusive
    }
}
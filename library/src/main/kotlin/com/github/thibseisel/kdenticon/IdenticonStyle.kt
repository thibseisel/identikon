package com.github.thibseisel.kdenticon

/**
 * Specifies the color style of an identicon.
 */
class IdenticonStyle {

    /**
     * The background color of the icon.
     * Set to `0x00000000` (transparent) to remove the background.
     */
    var backgroundColor = DEFAULT_BACK_COLOR

    /**
     * the padding between the edge of the image and the bounds of the rendered icon.
     * The value is specified in percent in the range [0.0, 0.4].
     */
    var padding = DEFAULT_PADDING
        set(value) {
            require(value in 0.0f .. 0.4f) { "Only padding values in the range [0.0, 0.4] are valid." }
            field = value
        }

    /**
     * The saturation of the icon in the range [0.0f, 1.0f].
     */
    var saturation = DEFAULT_SATURATION
        set(value) {
            require(value in 0.0f .. 1.0f) { "Only saturation values in range [0.0, 1.0] are allowed." }
            field = value
        }

    var colorLightness = DEFAULT_COLOR_LIGHTNESS
        set(range) {
            require(range.start in 0.0f .. 1.0f)
            require(range.endInclusive in 0.0f .. 1.0f)
            field = range
        }

    var grayScaleLightness = DEFAULT_GRAY_SCALE_LIGHTNESS
        set(range) {
            require(range.start in 0.0f .. 1.0f)
            require(range.endInclusive in 0.0f .. 1.0f)
            field = range
        }

    companion object {
        @JvmField val DEFAULT_COLOR_LIGHTNESS = (0.4f .. 0.8f)
        @JvmField val DEFAULT_GRAY_SCALE_LIGHTNESS = (0.3f .. 0.9f)

        const val DEFAULT_BACK_COLOR = 0xffffffff.toInt()
        const val DEFAULT_PADDING = 0.08f
        const val DEFAULT_SATURATION = 0.5f
    }
}
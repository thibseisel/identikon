package com.github.thibseisel.kdenticon

import com.github.thibseisel.kdenticon.rendering.IconGenerator
import com.github.thibseisel.kdenticon.rendering.PngRenderer
import com.github.thibseisel.kdenticon.rendering.Rectangle
import com.github.thibseisel.kdenticon.rendering.Renderer
import java.io.OutputStream
import java.security.MessageDigest

@Suppress("unused")
class Identicon(val hash: ByteArray, val size: Int) {

    init {
        require(hash.size > 5) { "The hash should be composed at least of 6 bytes." }
        require(size > 0) { "The size should be 1 pixel or larger." }
    }

    /**
     * The generator used to render this icon.
     *
     * This will be an instance of [IconGenerator] by default.
     * Clients may extends the existing [IconGenerator] class and set it to this icon
     * to provide their own custom shapes.
     */
    var generator = IconGenerator()

    /**
     * The style for this icon.
     * This dictates what kind of colors use when generating this icon.
     *
     * This will be initialized to the default style.
     */
    var style = IdenticonStyle()

    /**
     * Draws this icon using the specified renderer.
     *
     * This method is intended for usage with custom renderers.
     * A custom renderer could for example render an identicon is a file format not natively supported by KDenticon.
     *
     * @param renderer The renderer used to render this icon.
     * @param rect The bounds of the rendered icon. No padding will be applied to the rectangle.
     */
    fun draw(renderer: Renderer, rect: Rectangle) {
        generator.generate(renderer, rect, style, hash)
    }

    /**
     * Get the bounds of the icon excluding its padding.
     */
    fun getIconBounds() = Rectangle(
            (style.padding * size).toInt(),
            (style.padding * size).toInt(),
            size - (style.padding * size).toInt() * 2,
            size - (style.padding * size).toInt() * 2
    )

    fun saveAsPng(stream: OutputStream) {
        val renderer = PngRenderer(size, size)
        val iconBounds = this.getIconBounds()
        this.draw(renderer, iconBounds)
        renderer.savePng(stream)
    }

    companion object {

        /**
         * Creates an identicon from a specified hash.
         *
         * @param hash The hash tha will be used as base for this icon.
         * The hash must contain at least 6 bytes.
         * @param size The width and height or the icon in pixels.
         * The size must be a strictly positive number.
         * @return An identicon instance
         */
        @JvmStatic fun fromHash(hash: ByteArray, size: Int): Identicon {
            return Identicon(hash, size)
        }

        /**
         * Generates a hash from a specified value and creates an Identicon instance from the generated hash.
         * The hash is produced from the string representation of the provided `value` hashed by the SHA-1 algorithm.
         *
         * @param value An object to use as basis to generate the hash
         * @param size The width and height of the icon in pixels.
         * The size must be a strictly positive number.
         * @return An identicon instance
         */
        @JvmStatic fun fromValue(value: Any, size: Int): Identicon {
            val sha1 = MessageDigest.getInstance("SHA-1")
            sha1.update(value.toString().toByteArray())
            return Identicon(sha1.digest(), size)
        }
    }
}
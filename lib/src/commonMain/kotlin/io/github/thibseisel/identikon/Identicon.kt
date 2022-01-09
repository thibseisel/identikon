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

package io.github.thibseisel.identikon

import io.github.thibseisel.identikon.rendering.IconGenerator
import io.github.thibseisel.identikon.rendering.Rectangle
import io.github.thibseisel.identikon.rendering.Renderer

/**
 * An icon made of simple shapes generated from a hash sequence.
 *
 * Identicons are generally used as placeholder images for user that don't have an avatar.
 * If the same sequence is used to create the icon, then the icon's look will be the same.
 *
 * @property size The size of this icon in pixels.
 */
public class Identicon private constructor(
    private val hash: ByteArray,
    public val size: Int,
    private val style: IdenticonStyle,
) {
    init {
        require(hash.size > 5) { "Input hash should be composed at least of 6 bytes." }
        require(size > 0) { "Size should be 1 pixel or larger." }
    }

    /**
     * Draws this icon using the specified renderer.
     * This method is intended for usage with custom renderers.
     * A custom renderer could for example render an identicon is a file format not natively
     * supported by identikon.
     * @param renderer The renderer used to render this icon.
     */
    internal fun render(renderer: Renderer) {
        generator.generate(renderer, getIconBounds(), style, hash)
    }

    /**
     * Get the bounds of the icon, taking its padding into account.
     */
    private fun getIconBounds(): Rectangle {
        val scaledPadding = (style.padding * size).toInt()
        return Rectangle(
            x = scaledPadding,
            y = scaledPadding,
            width = size - scaledPadding * 2,
            height = size - scaledPadding * 2
        )
    }

    public companion object Factory {
        private val generator = IconGenerator()

        /**
         * Creates an identicon from a specified hash.
         *
         * @param hash The hash tha will be used as base for this icon.
         * The hash must contain at least 6 bytes.
         * @param size The width and height or the icon in pixels.
         * @param style Optional custom style to be applied to the generated icon.
         * If none is specified [the default style][IdenticonStyle.DEFAULT] will be used.
         * The size must be a strictly positive number.
         * @return An identicon instance.
         */
        public fun fromHash(
            hash: ByteArray,
            size: Int,
            style: IdenticonStyle = IdenticonStyle.DEFAULT,
        ): Identicon = Identicon(hash, size, style)

        /**
         * Generates a hash from a specified value and creates an Identicon instance from the generated hash.
         * The hash is produced from the string representation of the provided `value` hashed by the SHA-1 algorithm.
         *
         * @param value An object to use as basis to generate the hash.
         * @param size The width and height of the icon in pixels. It should be a strictly positive number.
         * @param style Optional custom style to be applied to the generated icon.
         * If none is specified [the default style][IdenticonStyle.DEFAULT] will be used.
         * @return An identicon instance.
         */
        public fun fromValue(
            value: Any,
            size: Int,
            style: IdenticonStyle = IdenticonStyle.DEFAULT,
        ): Identicon {
            val bytes = value.toString().encodeToByteArray()
            return Identicon(sha1Sum(bytes), size, style)
        }
    }
}

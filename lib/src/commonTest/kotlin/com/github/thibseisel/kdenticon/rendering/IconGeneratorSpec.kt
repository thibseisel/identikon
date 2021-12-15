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

import io.kotest.core.spec.style.DescribeSpec
import java.nio.ByteBuffer
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class IconGeneratorSpec : DescribeSpec({

    describe("The default hue generation algorithm") {
        val generator = IconGenerator()

        it("should always compute hue in [0, 1]") {
            val randomBytes = generateRandomBytes()
            val hue: Float = generator.computeHueInternal(randomBytes)
            assertTrue(hue in 0f..1f)
        }

        it("should return 0 as a floating point when bytes are all zero") {
            val bytes = ByteArray(4)
            val hue = generator.computeHueInternal(bytes)
            assertEquals(0f, hue, 0.001f)
        }
    }

    describe("The default octet selector algorithm") {
        val generator = IconGenerator()

        it("should always return a value in 0..255") {
            val bytes = byteArrayOf(-0xf, -0x7, 0x0, 0x7, 0xf)
            (0..4).forEach { index ->
                val octet = generator.getOctetInternal(bytes, index)
                assertTrue(octet in 0..255)
            }
        }
    }
})

private fun generateRandomBytes(): ByteArray {
    val randomLong = Random.nextLong()
    val buffer = ByteBuffer.allocate(8)
    buffer.putLong(randomLong)
    return buffer.array()
}

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

package com.github.thibseisel.identikon.rendering

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.floats.shouldBeBetween
import io.kotest.matchers.floats.shouldBeExactly
import io.kotest.matchers.ints.shouldBeBetween
import kotlin.random.Random

internal class IconGeneratorSpec : DescribeSpec({

    describe("The default hue generation algorithm") {
        val generator = IconGenerator()

        it("always computes hue in [0, 1]") {
            val randomBytes = generateRandomBytes()
            val hue: Float = generator.computeHue(randomBytes)
            hue.shouldBeBetween(0f, 1f, 0f)
        }

        it("returns 0 when bytes are all zero") {
            val bytes = ByteArray(4)
            val hue = generator.computeHue(bytes)
            hue.shouldBeExactly(0f)
        }
    }

    describe("The default octet selector algorithm") {
        val generator = IconGenerator()

        it("always returns a value in [0, 255]") {
            val bytes = byteArrayOf(-0xf, -0x7, 0x0, 0x7, 0xf)
            repeat(5) { index ->
                val octet = generator.getOctet(bytes, index)
                octet.shouldBeBetween(0, 255)
            }
        }
    }
})

private fun generateRandomBytes(): ByteArray {
    val bytes = ByteArray(8)
    return Random.nextBytes(bytes)
}

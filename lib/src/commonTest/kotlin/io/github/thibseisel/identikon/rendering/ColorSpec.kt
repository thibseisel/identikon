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

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalUnsignedTypes::class)
class ColorSpec : DescribeSpec({
    describe("Color.hex") {
        it("converts hex numbers to a Color") {
            withData(
                0x00000000u to "#00000000",
                0xffffffffu to "#ffffffff",
                0xaabbccddu to "#aabbccdd"
            ) { (hexCode, rgbaString) ->
                Color.hex(hexCode).toString() shouldBe rgbaString
            }
        }
    }

    describe("Color.rgba") {
        it("builds color from RGBA components") {
            withData(
                uintArrayOf(0x00u, 0x00u, 0x00u, 0x00u) to "#00000000",
                uintArrayOf(0xffu, 0xffu, 0xffu, 0xffu) to "#ffffffff",
                uintArrayOf(0xaau, 0xbbu, 0xccu, 0xddu) to "#aabbccdd",
                uintArrayOf(0x12u, 0x34u, 0x56u, 0x78u) to "#12345678",
            ) { (components, rgbaString) ->
                val (red, green, blue, alpha) = components
                Color.rgba(red, green, blue, alpha).toString() shouldBe rgbaString
            }
        }

        it("throws when some components are out of range") {
            withData(
                uintArrayOf(0x100u, 0x00u, 0x00u, 0xffu),
                uintArrayOf(0x00u, 0xffffu, 0xaau, 0xbcu),
                uintArrayOf(0x00u, 0x00u, 0xabcu, 0xffffffffu),
            ) { (red, green, blue, alpha) ->
                shouldThrow<IllegalArgumentException> {
                    Color.rgba(red, green, blue, alpha)
                }
            }
        }
    }

    describe("Color.hsl") {
        it("throws when any component does not fit in 0..1") {
            shouldThrow<IllegalArgumentException> { Color.hsl(1.2f, 0f, 0f) }
            shouldThrow<IllegalArgumentException> { Color.hsl(0f, -3f, 0f) }
            shouldThrow<IllegalArgumentException> { Color.hsl(0f, 0f, 16f) }
        }

        it("returns a shade of grey when saturation is 0") {
            // Shades of grey have the same value for each color channel
            val color = Color.hsl(0.67f, 0f, 0.88f)
            color.red shouldBe color.green
            color.green shouldBe color.blue
            // Implicit equality between red and blue channel values
        }

        it("always returns an opaque color") {
            // Opaque colors have the maximum alpha value
            Color.hsl(0f, 0f, 0f).alpha shouldBe 255u
            Color.hsl(1f, 1f, 1f).alpha shouldBe 255u
            Color.hsl(0.56f, 0.63f, 0.22f).alpha shouldBe 255u
        }

        it("should translate to the color red") {
            // In HSL, red is represented by the min and max hue (same point on the chromatic circle)
            Color.hsl(0f, 1f, 0.5f).toString() shouldBe "#ff0000ff"
        }

        it("should translate to the color green") {
            Color.hsl(1 / 3f, 1f, 0.5f).toString() shouldBe "#00ff00ff"
        }

        it("should translate to the color blue") {
            Color.hsl(2 / 3f, 1f, 0.5f).toString() shouldBe "#0000ffff"
        }

        it("should translate to black when lightness is 0") {
            Color.hsl(0.44f, 0.17f, 0f).toString() shouldBe "#000000ff"
        }

        it("should translate to white when lightness is 1") {
            Color.hsl(0.44f, 0.17f, 1f).toString() shouldBe "#ffffffff"
        }
    }

    describe("red, green, blue, alpha properties") {
        it("extracts components from RGBA color") {
            withData(
                Color.hex(0x00000000u) to uintArrayOf(0x00u, 0x00u, 0x00u, 0x00u),
                Color.hex(0xff000000u) to uintArrayOf(0xffu, 0x00u, 0x00u, 0x00u),
                Color.hex(0xaabbccddu) to uintArrayOf(0xaau, 0xbbu, 0xccu, 0xddu),
                Color.hex(0x12345678u) to uintArrayOf(0x12u, 0x34u, 0x56u, 0x78u),
            ) { (color, expectations) ->
                val (red, green, blue, alpha) = expectations
                color.alpha shouldBe alpha
                color.red shouldBe red
                color.green shouldBe green
                color.blue shouldBe blue
            }
        }
    }

    describe("toString") {
        it("encodes RGB components as a color code") {
            Color.rgba(0xaau, 0xbbu, 0xccu).toString() shouldBe "#aabbccff"
        }

        it("encodes RGBA components as a color code") {
            Color.rgba(0x12u, 0x34u, 0x56u, 0x78u).toString() shouldBe "#12345678"
        }
    }

    describe("toRgbString") {
        it("encodes RGB components as string") {
            withData(
                0x00000000u to "#000000",
                0xffffffffu to "#ffffff",
                0x12345678u to "#123456"
            ) { (rgba, expected) ->
                Color.hex(rgba).toRgbString() shouldBe expected
            }
        }
    }

    describe("equals") {
        it("compares Color.rgba") {
            val a = Color.rgba(0x7fu, 0x34u, 0x90u, 0xffu)
            val b = Color.rgba(0x7fu, 0x34u, 0x90u, 0xffu)
            a shouldBe b
        }

        it("compares Color.hex") {
            val a = Color.hex(0x7f3490ffu)
            val b = Color.hex(0x7f3490ffu)
            a shouldBe b
        }

        it("compares Color.rgba and Color.hex") {
            val rgba = Color.rgba(0x7fu, 0x34u, 0x90u, 0xffu)
            val hex = Color.hex(0x7f3490ffu)
            rgba shouldBe hex
        }
    }
})

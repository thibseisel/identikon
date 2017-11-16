package com.github.thibseisel.kdenticon.rendering

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals

class ColorSpec : Spek({

    describe("The sRGB color encoding algorithm") {

        it("should return the color code for transparent") {
            assertEquals(0x00000000, colorOf(0, 0, 0, 0))
        }

        it("should feature the alpha channel as the 24-31rd bits") {
            val encodedColor = colorOf(0x5A, 0x69, 0x01, 0xCE)
            val bitmask = 0xFF000000.toInt()
            assertEquals(0x5A000000, encodedColor and bitmask)
        }

        it("should feature the red channel as the 16-23th bits") {
            val encodedColor = colorOf(0x5A, 0x69, 0x01, 0xCE)
            val bitmask = 0xFF0000
            assertEquals(0x00690000, encodedColor and bitmask)
        }

        it("should feature the green channel as the 8-15th bits") {
            val encodedColor = colorOf(0x5A, 0x69, 0x01, 0xCE)
            val bitmask = 0xFF00
            assertEquals(0x00000100, encodedColor and bitmask)
        }

        it("should feature the blue channel as the 0-7th bits") {
            val encodedColor = colorOf(0x5A, 0x69, 0x01, 0xCE)
            val bitmask = 0xFF
            assertEquals(0x000000CE, encodedColor and bitmask)
        }

        it("should truncate extra bits from input values that does not fit in 0..255") {
            val encodedColor = colorOf(0, 0x123, 0xABCD, 0x987)
            val redChannel = encodedColor and 0xFF0000
            val greenChannel = encodedColor and 0xFF00
            val blueChannel = encodedColor and 0xFF

            assertEquals(0x230000, redChannel)
            assertEquals(0xCD00, greenChannel)
            assertEquals(0x87, blueChannel)
        }
    }

    describe("the color channel extension properties") {
        val color = 0xABE0738C.toInt()

        it("should extract the alpha channel") {
            assertEquals(0xAB, color.alpha)
        }

        it("should extract the red channel") {
            assertEquals(0xE0, color.red)
        }

        it("should extract the green channel") {
            assertEquals(0x73, color.green)
        }

        it("should extract the blue channel") {
            assertEquals(0x8C, color.blue)
        }
    }
})

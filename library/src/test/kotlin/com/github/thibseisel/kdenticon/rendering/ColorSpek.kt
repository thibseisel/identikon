package com.github.thibseisel.kdenticon.rendering

import com.github.thibseisel.kdenticon.assertFailsWith
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue

/**
 * Specifications for helper functions that manipulates colors.
 */
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

    describe("The color channel extension properties") {
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

    describe("The HSL to RGB color conversion algorithm") {

        it("should fail with IllegalArgumentException if any parameter does not fit in 0..1") {
            assertFailsWith<IllegalArgumentException> { colorFromHsl(1.2f, 0f, 0f) }
            assertFailsWith<IllegalArgumentException> { colorFromHsl(0f, -3f, 0f) }
            assertFailsWith<IllegalArgumentException> { colorFromHsl(0f, 0f, 16f) }
        }

        it("should return a shade of grey if saturation is 0") {
            // Shades of grey have the same value for each color channel
            val color = colorFromHsl(0.67f, 0f, 0.88f)
            assertTrue(color.red == color.green)
            assertTrue(color.green == color.blue)
            // Implicit equality between red and blue channel values
        }

        it("should always return an opaque color") {
            // Opaque colors have the maximum alpha value
            assertEquals(0xFF, colorFromHsl(0f, 0f, 0f).alpha)
            assertEquals(0xFF, colorFromHsl(1f, 1f, 1f).alpha)
            assertEquals(0xFF, colorFromHsl(0.56f, 0.63f, 0.22f).alpha)
        }

        it("should translate to the color red") {
            // In HSL, red is represented by the min and max hue (same point on the chromatic circle)
            assertEquals(0xFFFF0000.toInt(), colorFromHsl(0f, 1f, 0.5f))
            assertEquals(0xFFFF0000.toInt(), colorFromHsl(1f, 1f, 0.5f))
        }

        it("should translate to the color green") {
            assertEquals(0xFF00FF00.toInt(), colorFromHsl(1 / 3f, 1f, 0.5f))
        }

        it("should translate to the color blue") {
            assertEquals(0xFF0000FF.toInt(), colorFromHsl(2 / 3f, 1f, 0.5f))
        }
        it("should translate to black when lightness is 0") {
            assertEquals(0xFF000000.toInt(), colorFromHsl(0.44f, 0.17f, 0f))
        }

        it("should translate to white when lightness is 1") {
            assertEquals(0xFFFFFFFF.toInt(), colorFromHsl(0.44f, 0.17f, 1f))
        }
    }
})

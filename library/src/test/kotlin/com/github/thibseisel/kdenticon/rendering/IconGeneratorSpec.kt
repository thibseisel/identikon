package com.github.thibseisel.kdenticon.rendering

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.nio.ByteBuffer
import java.util.*

object IconGeneratorSpec : Spek({

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

fun generateRandomBytes(): ByteArray {
    val randomLong = Random().nextLong()
    val buffer = ByteBuffer.allocate(8)
    buffer.putLong(randomLong)
    return buffer.array()
}
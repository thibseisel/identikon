package com.github.thibseisel.kdenticon.rendering

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import java.nio.ByteBuffer
import java.util.*

object IconGeneratorSpec : Spek({

    describe("the hue generation algorithm") {
        val generator = IconGenerator()

        on("passing any input") {
            val randomBytes = generateRandomBytes()
            it("should compute hue in [0, 1]") {
                val hue: Float = generator.computeHue(randomBytes)
                assertTrue(hue in 0f..1f)
            }
        }

        on("passing the input value 'zero' as a byte array") {
            val bytes = byteArrayOf(0x0, 0x0, 0x0, 0x0)
            it("should return 0 as a floating point") {
                val hue = generator.computeHue(bytes)

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
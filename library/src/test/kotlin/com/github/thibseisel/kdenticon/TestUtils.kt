package com.github.thibseisel.kdenticon

import org.junit.Assert.fail

/**
 * Assert that the following block function throws a given [Exception].
 *
 * @param T The type of the expected exception
 */
inline fun <reified T : Throwable> assertFailsWith(block: () -> Unit) {
    try {
        block()
        fail("Exception of type ${T::class} should have been thrown")
    } catch (thr: Throwable) {
        if (thr !is T) {
            fail("Unexpected exception: $thr")
        }
    }
}
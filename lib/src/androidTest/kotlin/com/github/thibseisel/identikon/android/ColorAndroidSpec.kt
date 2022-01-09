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

package com.github.thibseisel.identikon.android

import com.github.thibseisel.identikon.rendering.Color
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

internal class ColorAndroidSpec : DescribeSpec({
    describe("toArgbInt") {
        it("transforms RGBA to ARGB") {
            withData(
                Color.hex(0x000000ffu) to 0xff000000,
                Color.hex(0xaabbccffu) to 0xffaabbcc,
                Color.hex(0xaabbcc7fu) to 0x7faabbcc
            ) { (color, argb) ->
                color.toArgbInt() shouldBe argb.toInt()
            }
        }
    }
})

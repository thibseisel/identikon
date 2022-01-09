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

package com.github.thibseisel.identikon.svg

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SvgPathSpec : DescribeSpec({
    describe("moveTo") {
        it("begins a path at coordinates") {
            val path = SvgPath().apply {
                moveTo(12.33f, 16.9f)
            }
            path.toString() shouldBe "M12.33 16.9"
        }
    }

    describe("lineTo") {
        it("draw lines from current position to absolute coordinates") {
            val path = SvgPath().apply {
                lineTo(12.33f, 16.9f)
                lineTo(12.33f, 0f)
                lineTo(0f, 0f)
            }
            path.toString() shouldBe "L12.33 16.9L12.33 0.0L0.0 0.0"
        }
    }

    describe("arcBy") {
        it("draws circle portions relative to current position") {
            val path = SvgPath().apply {
                arcBy(
                    xRadius = 3.5f,
                    yRadius = 1.2f,
                    xAxisRotation = 45f,
                    dxEnd = 24f,
                    dyEnd = 12.33f,
                    largeArc = false,
                    clockwise = true
                )
            }
            path.toString() shouldBe "a3.5,1.2 45.0 0,1 24.0,12.33"
        }
    }

    describe("close") {
        it("appends Z to the path") {
            val path = SvgPath().apply {
                close()
            }
            path.toString() shouldBe "Z"
        }
    }

    describe("multiple operations") {
        it("draws square") {
            val path = SvgPath().apply {
                moveTo(0f, 0f)
                lineTo(12.5f, 0f)
                lineTo(12.5f, 12.5f)
                lineTo(0f, 12.5f)
                close()
            }
            path.toString() shouldBe "M0.0 0.0L12.5 0.0L12.5 12.5L0.0 12.5Z"
        }
    }
})

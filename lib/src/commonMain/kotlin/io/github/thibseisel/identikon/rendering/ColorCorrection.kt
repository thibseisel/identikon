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

private val COMPENSATION_FACTORS = floatArrayOf(0.55f, 0.5f, 0.5f, 0.46f, 0.6f, 0.55f, 0.55f)

/**
 * Compensate the lightness for hues that appear to be darker than others.
 * @param lightness Lightness in the range [0, 1].
 * @param hue Normalized hue in the range [0, 1].
 */
internal fun compensateLightness(lightness: Float, hue: Float): Float {
    require(hue in 0f..1f) { "Hue should be in [0, 1]" }
    require(lightness in 0f..1f) { "Lightness should be in [0, 1]" }

    val compensation = COMPENSATION_FACTORS[(hue * 6 + 0.5f).toInt()]

    // Adjust the input lightness relative to the compensation
    val newLightness = when {
        lightness < 0.5f -> lightness * compensation * 2f
        else -> compensation + (lightness - 0.5f) * (1 - compensation) * 2f
    }

    return newLightness
}

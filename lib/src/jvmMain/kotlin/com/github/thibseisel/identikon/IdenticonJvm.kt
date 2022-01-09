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
package com.github.thibseisel.identikon

import com.github.thibseisel.identikon.svg.SvgRenderer
import java.io.IOException
import java.io.OutputStream

/**
 * Renders this icon as a SVG file to be sent by the given stream.
 * Callers are responsive for closing the passed stream.
 *
 * @param stream The stream on which SVG instructions should be sent.
 */
@Throws(IOException::class)
public fun Identicon.saveAsSvg(stream: OutputStream) {
    val renderer = SvgRenderer(size, size)
    render(renderer)
    renderer.save(stream.bufferedWriter())
}

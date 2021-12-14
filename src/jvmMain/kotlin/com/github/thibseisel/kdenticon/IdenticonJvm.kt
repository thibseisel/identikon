/*
 * Copyright 2017 Thibault Seisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.thibseisel.kdenticon

import com.github.thibseisel.kdenticon.rendering.SvgRenderer
import java.io.File
import java.io.OutputStream

/*
 * Copyright 2017 Thibault Seisel
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

/**
 * Save this icon to an SVG file.
 * This internally uses a [SvgRenderer].
 *
 * @param filepath path of the file to create.
 * Callers are responsible for adding the `.svg` file extension if desired.
 */
public fun Identicon.saveToSvgFile(filepath: String) {
    File(filepath).outputStream().use {
        saveAsSvg(it)
    }
}

/**
 * Renders this icon as a SVG file to be sent by the given stream.
 * Most of the time you'd want to save this icon to a file; in this case, prefer using
 * [saveToSvgFile].
 *
 * Callers are responsive for closing the passed stream.
 *
 * @param stream The stream on which SVG instructions should be sent.
 */
public fun Identicon.saveAsSvg(stream: OutputStream) {
    val renderer = SvgRenderer(size, size)
    draw(renderer, getIconBounds())
    stream.bufferedWriter().use {
        renderer.save(it, false)
    }
}

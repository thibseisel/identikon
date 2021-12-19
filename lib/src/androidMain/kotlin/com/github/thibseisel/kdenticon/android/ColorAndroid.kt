/*
 * Copyright 2021 Thibault Seisel
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

package com.github.thibseisel.kdenticon.android

import com.github.thibseisel.kdenticon.rendering.Color

/**
 * Express a color in a format that's suitable for Android.
 * On Android, colors are encoded as 32-bits integers where ARGB components are each
 * 8-bits integers.
 */
internal fun Color.toArgbInt(): Int {
    return ((alpha shl 24) or (red shl 16) or (green shl 8) or blue).toInt()
}

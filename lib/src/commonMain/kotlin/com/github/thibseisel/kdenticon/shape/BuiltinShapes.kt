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

package com.github.thibseisel.kdenticon.shape

import com.github.thibseisel.kdenticon.shape.inner.ChessShape
import com.github.thibseisel.kdenticon.shape.inner.CrossShape
import com.github.thibseisel.kdenticon.shape.inner.FourCirclesShape
import com.github.thibseisel.kdenticon.shape.inner.FourInverseCirclesShape
import com.github.thibseisel.kdenticon.shape.inner.FourShardsShape
import com.github.thibseisel.kdenticon.shape.inner.FourTrianglesShape
import com.github.thibseisel.kdenticon.shape.inner.InverseRhombusPartShape
import com.github.thibseisel.kdenticon.shape.inner.InverseWindmillShape
import com.github.thibseisel.kdenticon.shape.inner.InverseWindowShape
import com.github.thibseisel.kdenticon.shape.inner.SquareShape
import com.github.thibseisel.kdenticon.shape.inner.WindowShape
import com.github.thibseisel.kdenticon.shape.outer.LargeTriangleShape
import com.github.thibseisel.kdenticon.shape.outer.SmallTriangleShape
import com.github.thibseisel.kdenticon.shape.inner.QuarterDiskShape as InnerCircleShape
import com.github.thibseisel.kdenticon.shape.inner.RhombusPartShape as InnerRhombusShape
import com.github.thibseisel.kdenticon.shape.outer.CircleShape as OuterCircleShape
import com.github.thibseisel.kdenticon.shape.outer.RhombusShape as OuterRhombusShape

/**
 * Default definition for shapes that are placed in the center of the icon.
 * Those shapes are meant to render a complex shape at the center of the icon by combining 4 cells
 * representing the same shape, but rotated by 90° degrees.
 */
internal val INNER_SHAPES: List<ShapeDefinition> = listOf(
    InverseWindmillShape,
    FourTrianglesShape,
    SquareShape,
    WindowShape,
    FourCirclesShape,
    FourShardsShape,
    CrossShape,
    InnerRhombusShape,
    InverseRhombusPartShape,
    InverseWindowShape,
    FourInverseCirclesShape,
    ChessShape,
    InnerCircleShape
)

/**
 * Default definition of the shapes that are placed around the center of the icon.
 */
internal val OUTER_SHAPES: List<ShapeDefinition> = listOf(
    LargeTriangleShape,
    SmallTriangleShape,
    OuterRhombusShape,
    OuterCircleShape
)

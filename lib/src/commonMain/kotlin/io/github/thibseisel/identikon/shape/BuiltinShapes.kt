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

package io.github.thibseisel.identikon.shape

import io.github.thibseisel.identikon.shape.inner.ChessShape
import io.github.thibseisel.identikon.shape.inner.CrossShape
import io.github.thibseisel.identikon.shape.inner.FourCirclesShape
import io.github.thibseisel.identikon.shape.inner.FourInverseCirclesShape
import io.github.thibseisel.identikon.shape.inner.FourShardsShape
import io.github.thibseisel.identikon.shape.inner.FourTrianglesShape
import io.github.thibseisel.identikon.shape.inner.InverseRhombusPartShape
import io.github.thibseisel.identikon.shape.inner.InverseWindmillShape
import io.github.thibseisel.identikon.shape.inner.InverseWindowShape
import io.github.thibseisel.identikon.shape.inner.SquareShape
import io.github.thibseisel.identikon.shape.inner.WindowShape
import io.github.thibseisel.identikon.shape.outer.LargeTriangleShape
import io.github.thibseisel.identikon.shape.outer.SmallTriangleShape
import io.github.thibseisel.identikon.shape.inner.QuarterDiskShape as InnerCircleShape
import io.github.thibseisel.identikon.shape.inner.RhombusPartShape as InnerRhombusShape
import io.github.thibseisel.identikon.shape.outer.CircleShape as OuterCircleShape
import io.github.thibseisel.identikon.shape.outer.RhombusShape as OuterRhombusShape

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

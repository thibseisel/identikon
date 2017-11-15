package com.github.thibseisel.kdenticon.shape

/**
 * Represents a shape to be rendered in an icon.
 * These instances are hash-specific and will be rendered in each cell.
 */
class Shape(
        /**
         * The shape definition to be used to render the shape.
         */
        val definition: ShapeDefinition,
        /**
         * The fill color of the shape.
         */
        val color: Int,
        /**
         * The rotation index of the icon in the first position.
         */
        val startRotationIndex: Int,
        /**
         * The positions in which the shape will be rendered.
         */
        val positions: Array<ShapePosition>
)
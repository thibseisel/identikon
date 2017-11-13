package com.github.thibseisel.javadenticon.shape

/**
 * Represents a category of shapes that can be rendered in an icon.
 * These instances are not hash-specific.
 */
class ShapeCategory(
        /**
         * The index of the hash octet determining the color of shapes in this category.
         */
        val colorIndex: Int,
        /**
         * A list of possible shape definitions for this category.
         */
        val shapes: Array<out ShapeDefinition>,
        /**
         * The index of the hash octet determining which of the shape definitions
         * will be used for a particular hash.
         */
        val shapeIndex: Int,
        /**
         * The index of the hash octet determining the rotation index of the shape
         * in the first position.
         */
        val rotationIndex: Int?,
        /**
         * The positions in which the shapes of this category will be rendered.
         */
        val positions: Array<ShapePosition>
)
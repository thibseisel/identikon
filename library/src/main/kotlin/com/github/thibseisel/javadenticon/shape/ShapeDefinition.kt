package com.github.thibseisel.javadenticon.shape

import com.github.thibseisel.javadenticon.rendering.Renderer

/**
 * Defines instructions used to render a shape.
 *
 * Shapes are drawn on the context of their container cell.
 * This means that for all points to be contained in the cell,
 * they must be defined in `[0.0, cell] x [0.0, cell]`.
 */
interface ShapeDefinition {

    /**
     * Renders a shape in the specified drawing context.
     * Clients may call any method(s) of [renderer] to define their shape.
     * While it is advised to render only in the bounds of the containing cell,
     * it is not required to do so.
     *
     * @param renderer Renderer used to render the shape
     * @param cell The bounds of the cell in which the shape is rendered
     * @param index The zero-based index of the current shape position
     */
    fun render(renderer: Renderer, cell: Int, index: Int)
}
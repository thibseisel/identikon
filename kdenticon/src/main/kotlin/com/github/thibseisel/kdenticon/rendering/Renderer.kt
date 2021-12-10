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

package com.github.thibseisel.kdenticon.rendering

import com.github.thibseisel.kdenticon.Identicon

/**
 * Base class for rendering shapes in an identicon.
 * This class acts as a proxy to delegate instructions to draw shapes onto any kind of surface
 * or data streams.
 *
 * You may extend this class, for example, to support a new file format that is not natively
 * supported by Kdenticon or directly draw the icon onto a surface.
 *
 * To use your own implementation of a renderer, pass it as an argument to the [Identicon.draw] method.
 *
 * @see SvgRenderer
 */
public abstract class Renderer {

    /**
     * The current transform that will be applied on all coordinates before being rendered
     * in the target image.
     */
    internal var transform: Transform = Transform.EMPTY

    /**
     * Adds a polygon without translating or changing direction of the points.
     *
     * @param points The coordinates of the points the polygon consists of.
     */
    protected abstract fun addPolygonNoTransform(points: Array<PointF>)

    /**
     * Adds a circle without translating of its border.
     *
     * @param location The upper-left position of the bounding rectangle
     * @param diameter The diameter of the circle
     * @param counterClockwise If set to `true`, the circle will be drawn counter clockwise
     */
    protected abstract fun addCircleNoTransform(
        location: PointF,
        diameter: Float,
        counterClockwise: Boolean,
    )

    /**
     * Sets the background color for the whole icon.
     *
     * @param color The color to use as a background, represented as a sRGB-encoded 32 bits integer
     */
    public abstract fun setBackground(color: Int)

    /**
     * Performs the rendering of a new shape.
     * Implementations should use the provided [action] to start the drawing operations.
     *
     * This allow implementations to perform actions before and after the actual drawing
     * defined by [action], for example open or close a data stream.
     *
     * @param color The fill color of the shape.
     * @param action The actual drawing of the shapes.
     */
    public abstract fun renderShape(color: Int, action: () -> Unit)

    /**
     * Renders a polygon defined by the coordinates of its vertexes.
     * This method apply this renderer's configured transformation to each point.
     *
     * @param points The coordinates of the points this polygon consists of.
     * @param invert If `true` the area of the polygon will be removed from the filled area.
     */
    private fun addPolygonInternal(points: Array<PointF>, invert: Boolean) {
        if (invert) {
            points.reverse()
        }

        for (i in points.indices) {
            val (x, y) = points[i]
            points[i] = transform.transformPoint(x, y)
        }

        addPolygonNoTransform(points)
    }

    /**
     * Adds a rectangle to the icon.
     *
     * Rectangles are defined from the coordinates of their top-left vertex, their width and their height.
     * For example, calling `addRectangle(0f, 0f, 4f, 3f)` will render a rectangle whose points coordinates are :
     *
     * - (0.0, 0.0) for the top-left vertex,
     * - (4.0, 0.0) for the top-right vertex,
     * - (4.0, 3.0) for the bottom-right vertex,
     * - (0.0, 3.0) for the bottom-left vertex.
     *
     * @param x The X-coordinate of the top-left vertex of the rectangle.
     * @param y The Y-coordinate of the top-left vertex of the rectangle.
     * @param width Width of the rectangle.
     * @param height Height of the rectangle.
     */
    public fun addRectangle(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        invert: Boolean = false,
    ) {
        addPolygonInternal(
            arrayOf(
                PointF(x, y),
                PointF(x + width, y),
                PointF(x + width, y + height),
                PointF(x, y + height)
            ), invert
        )
    }

    /**
     * Adds a circle to the image.
     *
     * Circles are not defined by the coordinates of their center and their diameter;
     * instead they are included in a bounding rectangle of the specified coordinates and size.
     *
     * @param x The X-coordinate of the top-left vertex of the bounding rectangle.
     * @param y The Y-coordinate of the top-left vertex of the bounding rectangle.
     * @param size Width and height of the bounding rectangle.
     * @param invert If `true` the area of the circle will be removed from the filled area.
     */
    public fun addCircle(x: Float, y: Float, size: Float, invert: Boolean = false) {
        val northWest = transform.transformPoint(x, y, size, size)
        addCircleNoTransform(northWest, size, invert)
    }

    /**
     * Adds a polygon to the image.
     * Polygons are rendered in the order their points are defined.
     *
     * @param points The coordinates of the points this polygon consists of.
     * @param invert If `true` the area of the polygon will be removed from the filled area.
     */
    public fun addPolygon(points: Array<PointF>, invert: Boolean = false) {
        addPolygonInternal(points.clone(), invert)
    }

    /**
     * Adds a triangle to the image.
     *
     * @param x X-coordinate of the top-left vertex of the bounding rectangle.
     * @param y Y-coordinate of the top-left vertex of the bounding rectangle.
     * @param width Width of the bounding rectangle.
     * @param height Height of the bounding rectangle.
     * @param direction The direction in which the 90° angle of this triangle points to,
     * as defined by [TriangleDirection].
     * @param invert If `true` the area of the triangle will be removed from the filled area.
     */
    public fun addTriangle(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        direction: Int,
        invert: Boolean = false,
    ) {
        val points = mutableListOf(
            PointF(x + width, y),
            PointF(x + width, y + height),
            PointF(x, y + height),
            PointF(x, y)
        )

        points.removeAt(direction)
        addPolygonInternal(points.toTypedArray(), invert)
    }

    /**
     * Adds a rhombus to the image.
     *
     * @param x X-coordinate of the bounding rectangle.
     * @param y Y-coordinate of the bounding rectangle.
     * @param width Width of the bounding rectangle.
     * @param height Height of the bounding rectangle.
     * @param invert If `true` the area of the rhombus will be removed from the filled area.
     */
    public fun addRhombus(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        invert: Boolean = false,
    ) {
        addPolygonInternal(
            arrayOf(
                PointF(x + width / 2, y),
                PointF(x + width, y + height / 2),
                PointF(x + width / 2, y + height),
                PointF(x, y + height / 2)
            ), invert
        )
    }
}

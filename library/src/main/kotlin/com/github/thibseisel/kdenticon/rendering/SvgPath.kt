package com.github.thibseisel.kdenticon.rendering

/**
 * Represents a SVG path element.
 *
 * This class can be used as a builder: you may call any `add*` method to add drawings to the SVG path in order,
 * then call [toString] to obtain it as a string that can be written to a SVG file.
 */
internal class SvgPath {

    private val pathBuilder = StringBuilder()

    fun addCircle(location: PointF, diameter: Float, counterClockwise: Boolean) {
        TODO()
    }

    fun addPolygon(points: Array<PointF>) {
        TODO()
    }

    override fun toString(): String {
        return pathBuilder.toString()
    }
}
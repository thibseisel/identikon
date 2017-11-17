package com.github.thibseisel.kdenticon.rendering

/**
 * Represents a SVG path element.
 *
 * This class can be used as a builder: you may call any `add*` method to add drawings to the SVG path in order,
 * then call [toString] to obtain it as a string that can be written to a SVG file.
 */
internal class SvgPath {

    private val pathBuilder = StringBuilder()

    /**
     * Adds a circle to this SVG path.
     * @param location
     * @param diameter
     * @param counterClockwise
     */
    fun addCircle(location: PointF, diameter: Float, counterClockwise: Boolean) {
        // Circles are drawn using "arc-to" SVG instructions

        val sweepFlag = if (counterClockwise) "0" else "1"
        val radius = diameter / 2f

        pathBuilder.append("""M${location.x} ${location.y + radius}
            a$radius,$radius 0 1, $sweepFlag ${diameter},0
            a$radius,$radius 0 1, $sweepFlag ${-diameter},0""".trimIndent())
    }

    /**
     * Adds a polygon to this SVG path.
     * @param points the points this polygon consists of.
     */
    fun addPolygon(points: Array<PointF>) {
        // Prevent failures if the polygon has no point
        if (points.isNotEmpty()) {

            // The first point of this path defined with an M command
            pathBuilder.append("M${points[0].x} ${points[0].y}")

            for (point in points) {
                // Draw segments using absolute Line-to commands
                pathBuilder.append("L${point.x} ${point.y}")
            }

            // Close the path with a Z command
            pathBuilder.append("Z")
        }
    }

    override fun toString(): String {
        return pathBuilder.toString()
    }
}
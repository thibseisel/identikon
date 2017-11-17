package com.github.thibseisel.kdenticon.rendering

import com.github.thibseisel.kdenticon.Colors
import java.io.Writer
import java.util.*

/**
 * A Renderer implementation that renders icons as SVG path instructions.
 *
 * @constructor Creates a new instance of a SvgRenderer.
 * @param width The width of the icon in pixels.
 * @param height The height of the icon in pixels.
 */
class SvgRenderer(
        private val width: Int,
        private val height: Int
) : Renderer() {

    private val pathsByColor = HashMap<Int, SvgPath>()

    private lateinit var path: SvgPath
    private var backgroundColor = Colors.TRANSPARENT

    override fun renderShape(color: Int, action: Runnable) {
        path = pathsByColor.getOrPut(color, ::SvgPath)
        action.run()
    }

    override fun setBackground(color: Int) {
        backgroundColor = color
    }

    override fun addCircleNoTransform(location: PointF, diameter: Float, counterClockwise: Boolean) {
        path.addCircle(location, diameter, counterClockwise)
    }

    override fun addPolygonNoTransform(points: Array<PointF>) {
        path.addPolygon(points)
    }

    /**
     * Write SVG instructions with the specified Writer.
     * @param writer The output writer to which the SVG will be written.
     * @param partial If `true` an SVG string without the root svg tag will be rendered.
     */
    fun save(writer: Writer, partial: Boolean) {

        // Add SVG root element tag if requested
        if (!partial) {
            writer.write("""
                <svg xmlns="http://www.w3.org/2000/svg" width="$width" height="$height"
                viewBox="0 0 $width $height" preserveAspectRatio="xMidYMid meet">
                """.trimIndent())
        }

        // Draw the background only if it is not transparent
        if (backgroundColor.alpha > 0) {
            val opacity = backgroundColor.alpha / 255f
            writer.write("""
                <rect fill="${backgroundColor.toColorString()}" fill-opacity="$opacity"
                x="0" y="0" width="$width" height="$height" />
            """.trimIndent())
        }

        // Define each shape as an SVG path
        for (path in pathsByColor.values) {
            writer.write("""<path fill="${backgroundColor.toColorString()}" d="$path" />""")
        }

        // Close the SVG root element if requested
        if (!partial) {
            writer.write("</svg>")
        }
    }
}

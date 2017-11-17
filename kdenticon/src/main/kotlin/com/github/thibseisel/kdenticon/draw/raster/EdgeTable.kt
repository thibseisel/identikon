package com.github.thibseisel.kdenticon.draw.raster

import java.util.*

/**
 * Holds all intersecting edges for each scanline in the rendered viewport.
 */
internal class EdgeTable(height: Int) {

    init {
        require(height > 0)
    }

    private val scanlines = arrayOfNulls<MutableList<EdgeIntersectionRange>>(height)

    fun add(edge: Edge) {
        val minY: Int
        val maxY: Int

        when {
            // Skip horizontal lines
            edge.from.y == edge.to.y -> return
            edge.from.y < edge.to.y -> {
                minY = edge.from.y.toInt()
                // 0.996f is a rounded value of 1/255
                maxY = (edge.to.y + 0.996f).toInt()
            }
            else -> {
                minY = edge.to.y.toInt()
                // 0.996f is a rounded value of 1/255
                maxY = (edge.from.y + 0.996f).toInt()
            }
        }

        if (minY < maxY) {
            var y = minY
            var x1 = edge.intersection(y.toFloat())

            while (y < maxY) {
                val x2 = edge.intersection((y + 1).toFloat())

                var fromX: Int
                var width: Int

                if (x1 < x2) {
                    fromX = x1.toInt()
                    width = (x2 + 0.9999f).toInt() - fromX
                } else {
                    fromX = x2.toInt()
                    width = (x1 + 0.9999f).toInt() - fromX
                }

                if (fromX < 0) {
                    width += fromX
                    fromX = 0

                    if (width < 0) width = 0
                }

                var scanline = scanlines[y]
                if (scanline == null) {
                    scanline = ArrayList<EdgeIntersectionRange>()
                    scanlines[y] = scanline
                }

                scanline.add(EdgeIntersectionRange(fromX, width, edge))

                x1 = x2
                y++
            }
        }
    }

    fun sort() {
        for (line in scanlines) {
            line?.sortWith(Comparator { x, y ->
                if (x == null || y == null) throw NullPointerException()
                x.fromX - y.fromX
            })
        }
    }

    operator fun get(index: Int): List<EdgeIntersectionRange> {
        return scanlines[index].orEmpty()
    }
}


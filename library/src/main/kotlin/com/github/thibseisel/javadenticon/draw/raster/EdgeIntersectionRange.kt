package com.github.thibseisel.javadenticon.draw.raster

/**
 * Specified a segment of a scanline that is intersected by an edge.
 */
internal class EdgeIntersectionRange(
        val fromX: Int,
        val width: Int,
        val edge: Edge
)
package com.github.thibseisel.kdenticon.draw.raster

/**
 * Specified a segment of a scanline that is intersected by an edge.
 */
internal class EdgeIntersectionRange(
        val fromX: Int,
        val width: Int,
        val edge: Edge
)
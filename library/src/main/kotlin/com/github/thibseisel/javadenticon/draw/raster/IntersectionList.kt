package com.github.thibseisel.javadenticon.draw.raster

internal class IntersectionList {

    private lateinit var intersections: Array<Intersection?>
    var count: Int = 0

    fun initialize(capacity: Int) {
        intersections = intersections.takeIf { it.size >= capacity }
                ?: arrayOfNulls(capacity)
    }

    fun clear() {
        count = 0
    }

    fun add(edge: Edge, x: Float) {
        TODO()
    }
}
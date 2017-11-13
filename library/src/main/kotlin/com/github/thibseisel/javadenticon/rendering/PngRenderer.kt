package com.github.thibseisel.javadenticon.rendering

import java.io.OutputStream

class PngRenderer(width: Int, height: Int) : Renderer() {

    override fun renderShape(color: Int, action: Runnable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addPolygonNoTransform(points: Array<PointF>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCircleNoTransform(location: PointF, diameter: Float, counterClockwise: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBackground(color: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun savePng(stream: OutputStream) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
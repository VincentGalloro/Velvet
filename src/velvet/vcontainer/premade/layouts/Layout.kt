package velvet.vcontainer.premade.layouts

import velvet.structs.Bounds
import velvet.structs.Vector
import java.lang.Integer.max

interface Layout {

    fun getBounds(index: Int, bounds: Bounds): Bounds
}

class ColumnLayout(val height: Double, val sep: Double) : Layout{

    override fun getBounds(index: Int, bounds: Bounds) =
            bounds.setHeight(height, 0.0).localMove(Vector(0.0, (height+sep)*index))
}

class RowLayout(val width: Double, val sep: Double) : Layout{

    override fun getBounds(index: Int, bounds: Bounds) =
            bounds.setWidth(width, 0.0).localMove(Vector((width+sep)*index, 0.0))
}

class VariableColumnLayout(val heights: List<Double>,
                           val defaultHeight: Double,
                           seps: List<Double>,
                           val defaultSep: Double) : Layout {

    val yPositions: MutableList<Double> = mutableListOf(0.0)

    init {
        repeat(max(heights.size, seps.size)) {
            yPositions.add(yPositions.last() +
                    heights.getOrElse(it) { defaultHeight } +
                    seps.getOrElse(it) { defaultSep })
        }
    }

    override fun getBounds(index: Int, bounds: Bounds) =
            bounds.setHeight(heights.getOrElse(index) { defaultHeight }, 0.0)
                    .localMove(Vector(0.0, (yPositions.getOrElse(index) {
                        if(index < 0) {
                            yPositions.first() + (defaultHeight + defaultSep) * index
                        }
                        else {
                            yPositions.last() + (defaultHeight + defaultSep) * (index - yPositions.size + 1)
                        }
                    })))
}

class VariableRowLayout(val widths: List<Double>,
                        val defaultWidth: Double,
                        seps: List<Double>,
                        val defaultSep: Double) : Layout {

    val xPositions: MutableList<Double> = mutableListOf(0.0)

    init {
        repeat(max(widths.size, seps.size)) {
            xPositions.add(xPositions.last() +
                    widths.getOrElse(it) { defaultWidth } +
                    seps.getOrElse(it) { defaultSep })
        }
    }

    override fun getBounds(index: Int, bounds: Bounds) =
            bounds.setHeight(widths.getOrElse(index) { defaultWidth }, 0.0)
                    .localMove(Vector(0.0, (xPositions.getOrElse(index) {
                        if (index < 0) {
                            xPositions.first() + (defaultWidth + defaultSep) * index
                        }
                        else {
                            xPositions.last() + (defaultWidth + defaultSep) * (index - xPositions.size + 1)
                        }
                    })))
}
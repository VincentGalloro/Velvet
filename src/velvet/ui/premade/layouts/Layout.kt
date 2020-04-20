package velvet.ui.premade.layouts

import velvet.structs.Bounds
import velvet.structs.Vector
import java.lang.Integer.max
import kotlin.math.ceil
import kotlin.math.floor

interface Layout {

    fun getBounds(index: Int, bounds: Bounds, scroll: Double = 0.0): Bounds
    fun getVisibleRange(bounds: Bounds, scroll: Double): IntRange
}

class ColumnLayout(val heightGen: (Bounds)->Double, val sep: Double) : Layout{

    constructor(height: Double, sep: Double): this({ height }, sep)

    override fun getBounds(index: Int, bounds: Bounds, scroll: Double) =
            bounds.setHeight(heightGen(bounds), 0.0).localMove(Vector(0.0, (heightGen(bounds)+sep)*(index - scroll)))

    override fun getVisibleRange(bounds: Bounds, scroll: Double) =
            floor(scroll).toInt() .. floor(scroll).toInt() + ceil(bounds.size.y/(heightGen(bounds)+sep)).toInt()
}

class RowLayout(val widthGen: (Bounds)->Double, val sep: Double) : Layout{

    constructor(width: Double, sep: Double): this({ width }, sep)

    override fun getBounds(index: Int, bounds: Bounds, scroll: Double) =
            bounds.setWidth(widthGen(bounds), 0.0).localMove(Vector((widthGen(bounds)+sep)*(index-scroll), 0.0))

    override fun getVisibleRange(bounds: Bounds, scroll: Double) =
            floor(scroll).toInt() .. floor(scroll).toInt() + ceil(bounds.size.x/(widthGen(bounds)+sep)).toInt()
}

class GridLayout(sizeGen: (Bounds)->Vector, sep: Vector): Layout{

    constructor(height: Double, itemsPerRow: Int, sep: Vector):
            this({ Vector((it.size.x - sep.x*(itemsPerRow-1))/itemsPerRow, height) }, sep)

    private val columnLayout = ColumnLayout({ sizeGen(it).y }, sep.y)
    private val rowLayout = RowLayout({ sizeGen(it).x }, sep.x)

    private fun getItemsPerRow(bounds: Bounds) =
            ((bounds.size.x + rowLayout.sep) / (rowLayout.widthGen(bounds) + rowLayout.sep)).toInt()

    override fun getBounds(index: Int, bounds: Bounds, scroll: Double): Bounds{
        val itemsPerRow = getItemsPerRow(bounds)
        return rowLayout.getBounds(Math.floorMod(index, itemsPerRow),
                columnLayout.getBounds(Math.floorDiv(index, itemsPerRow), bounds, scroll))
    }

    override fun getVisibleRange(bounds: Bounds, scroll: Double): IntRange{
        val cRange = columnLayout.getVisibleRange(bounds, scroll)
        val itemsPerRow = getItemsPerRow(bounds)
        return cRange.first * itemsPerRow until (cRange.last+1) * itemsPerRow
    }
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

    override fun getBounds(index: Int, bounds: Bounds, scroll: Double) =
            bounds.setHeight(heights.getOrElse(index) { defaultHeight }, 0.0)
                    .localMove(Vector(0.0, (yPositions.getOrElse(index) {
                        if(index < 0) {
                            yPositions.first() + (defaultHeight + defaultSep) * index
                        }
                        else {
                            yPositions.last() + (defaultHeight + defaultSep) * (index - yPositions.size + 1)
                        }
                    })))

    override fun getVisibleRange(bounds: Bounds, scroll: Double) = 0 .. 0
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

    override fun getBounds(index: Int, bounds: Bounds, scroll: Double) =
            bounds.setWidth(widths.getOrElse(index) { defaultWidth }, 0.0)
                    .localMove(Vector(0.0, (xPositions.getOrElse(index) {
                        if (index < 0) {
                            xPositions.first() + (defaultWidth + defaultSep) * index
                        }
                        else {
                            xPositions.last() + (defaultWidth + defaultSep) * (index - xPositions.size + 1)
                        }
                    })))

    override fun getVisibleRange(bounds: Bounds, scroll: Double) = 0 .. 0
}
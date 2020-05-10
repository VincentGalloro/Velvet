package velvet.ui.layouts

import velvet.structs.Bounds
import velvet.structs.Vector

interface LLayout {

    companion object{

        fun columnLayout(height: Double, sep: Double): LLayout =
                StaticColumnLayout(height, sep)
        fun relativeColumnLayout(heightRatio: Double, sepRatio: Double): LLayout =
                RelativeColumnLayout(heightRatio, sepRatio)
        fun spreadColumnLayout(sep: Double): LLayout =
                SpreadColumnLayout(sep)

        fun rowLayout(width: Double, sep: Double): LLayout =
                StaticRowLayout(width, sep)
        fun relativeRowLayout(widthRatio: Double, sepRatio: Double): LLayout =
                RelativeRowLayout(widthRatio, sepRatio)
        fun spreadRowLayout(sep: Double): LLayout =
                SpreadRowLayout(sep)

        fun gridLayout(itemsPerRow: Int, heightRatio: Double, sep: Vector): LLayout =
                GridLayout(itemsPerRow, heightRatio, sep)
    }

    fun getBounds(bounds: Bounds, index: Int, total: Int, scroll: Double = 0.0): Bounds
}


private class StaticColumnLayout(val height: Double, val sep: Double) : LLayout{
    override fun getBounds(bounds: Bounds, index: Int, total: Int, scroll: Double) =
            bounds.setHeight(height, 0.0).localMove(Vector(0.0, height+sep)*(index - scroll))
}
private class RelativeColumnLayout(val heightRatio: Double, val sepRatio: Double): LLayout{
    override fun getBounds(bounds: Bounds, index: Int, total: Int, scroll: Double): Bounds {
        val height = bounds.size.x * heightRatio
        val sep = bounds.size.x * sepRatio
        return bounds.setHeight(height, 0.0).localMove(Vector(0.0, height + sep) * (index - scroll))
    }
}
private class SpreadColumnLayout(val sep: Double): LLayout{
    override fun getBounds(bounds: Bounds, index: Int, total: Int, scroll: Double): Bounds {
        val height = (bounds.size.y - sep*(total-1)) / total
        return bounds.setHeight(height, 0.0).localMove(Vector(0.0, height + sep) * (index - scroll))
    }
}

private class StaticRowLayout(val width: Double, val sep: Double) : LLayout{
    override fun getBounds(bounds: Bounds, index: Int, total: Int, scroll: Double) =
            bounds.setWidth(width, 0.0).localMove(Vector(width+sep, 0.0)*(index - scroll))
}
private class RelativeRowLayout(val widthRatio: Double, val sepRatio: Double): LLayout{
    override fun getBounds(bounds: Bounds, index: Int, total: Int, scroll: Double): Bounds {
        val width = bounds.size.y * widthRatio
        val sep = bounds.size.y * sepRatio
        return bounds.setWidth(width, 0.0).localMove(Vector(width + sep, 0.0) * (index - scroll))
    }
}
private class SpreadRowLayout(val sep: Double): LLayout{
    override fun getBounds(bounds: Bounds, index: Int, total: Int, scroll: Double): Bounds {
        val width = (bounds.size.x - sep*(total-1)) / total
        return bounds.setWidth(width, 0.0).localMove(Vector(width + sep, 0.0) * (index - scroll))
    }
}

private class GridLayout(val itemsPerRow: Int, heightRatio: Double, sep: Vector): LLayout {
    val rowLayout = SpreadRowLayout(sep.x)
    val columnLayout = RelativeColumnLayout(heightRatio, sep.y)

    override fun getBounds(bounds: Bounds, index: Int, total: Int, scroll: Double) =
            columnLayout.getBounds(
                    rowLayout.getBounds(bounds, index%itemsPerRow, itemsPerRow),
                    index/itemsPerRow,
                    (total-index-1)/itemsPerRow+1,
                    scroll)
}
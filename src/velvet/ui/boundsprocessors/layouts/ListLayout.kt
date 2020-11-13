package velvet.ui.boundsprocessors.layouts

import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Vector
import velvet.ui.components.functional.ScrollComponent

class ColumnListLayoutFactory(val layout: Layout){

    private fun compile(listLayout: ListLayout) = CompiledListLayout(layout, listLayout)

    fun fixedHeight(height: Double, sep: Double = 0.0)
            = compile(FixedColumnLayout(height, sep))
    fun scaledHeight(height: Double, sepScale: Double = 0.0, sepHeight: Double = 0.0)
            = compile(ScaledColumnLayout(height, sepScale, sepHeight))
}

class RowListLayoutFactory(val layout: Layout){

    private fun compile(listLayout: ListLayout) = CompiledListLayout(layout, listLayout)

    fun fixedWidth(width: Double, sep: Double = 0.0)
            = compile(FixedRowLayout(width, sep))
    fun scaledWidth(width: Double, sepScale: Double = 0.0, sepWidth: Double = 0.0)
            = compile(ScaledRowLayout(width, sepScale, sepWidth))
}

class CompiledListLayout(private val layout: Layout,
                         private val listLayout: ListLayout){
    fun noScroll()
            = layout.add { it, index -> listLayout.getBounds(it, index, 0.0) }
    fun bind(scrollComponent: ScrollComponent)
            = layout.add { it, index -> listLayout.getBounds(it, index, scrollComponent.scroll) }
}

interface ListLayout {
    fun getBounds(bounds: Bounds, index: Int, scroll: Double): Bounds
}

class FixedColumnLayout(val height: Double,
                        val sep: Double) : ListLayout{
    override fun getBounds(bounds: Bounds, index: Int, scroll: Double)
            = bounds.setHeight(height, 0.0).localMove(Vector(0.0, (height + sep) * (index - scroll)))
}

class ScaledColumnLayout(val heightScale: Double,
                         val sepScale: Double,
                         val sepHeight: Double): ListLayout{
    override fun getBounds(bounds: Bounds, index: Int, scroll: Double): Bounds {
        val height = bounds.size.width * heightScale
        val sep = bounds.size.width * sepScale + sepHeight
        return bounds.setHeight(height, 0.0).localMove(Vector(0.0, (height + sep) * (index - scroll)))
    }
}

class FixedRowLayout(val width: Double,
                     val sep: Double) : ListLayout{
    override fun getBounds(bounds: Bounds, index: Int, scroll: Double)
            = bounds.setWidth(width, 0.0).localMove(Vector((width + sep) * (index - scroll), 0.0))
}

class ScaledRowLayout(val widthScale: Double,
                      val sepScale: Double,
                      val sepWidth: Double): ListLayout{
    override fun getBounds(bounds: Bounds, index: Int, scroll: Double): Bounds {
        val width = bounds.size.height * widthScale
        val sep = bounds.size.height * sepScale + sepWidth
        return bounds.setWidth(width, 0.0).localMove(Vector((width + sep) * (index - scroll), 0.0))
    }
}
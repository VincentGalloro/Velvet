package velvet.ui.boundsprocessors.layouts

import velvet.util.types.spatial.Bounds
import java.lang.Double.max
import java.lang.Double.min

class VerticalSplitFactory(private val layout: Layout){

    private fun compile(verticalSplitLayout: VerticalSplitLayout)
            = CompiledVerticalSplitLayout(layout, verticalSplitLayout)

    fun half(sepScale: Double = 0.0, sepPad: Double = 0.0)
            = compile(VerticalSplitDoubleScaledLayout(0.5 - sepScale/2, sepPad/2, 0.5 - sepScale/2, sepPad/2))

    fun topScaled(topScale: Double, sepScale: Double = 0.0, sepPad: Double = 0.0)
            = compile(VerticalSplitDoubleScaledLayout(topScale, 0.0, 1 - topScale - sepScale, sepPad))
    fun bottomScaled(bottomScale: Double, sepScale: Double = 0.0, sepPad: Double = 0.0)
            = compile(VerticalSplitDoubleScaledLayout(1 - bottomScale - sepScale, sepPad, bottomScale, 0.0))

    fun topHeight(topHeight: Double, sepHeight: Double = 0.0)
            = compile(VerticalSplitFixedLeftLayout(topHeight, sepHeight))
    fun bottomHeight(bottomScale: Double, sepHeight: Double = 0.0)
            = compile(VerticalSplitFixedRightLayout(bottomScale, sepHeight))

    fun fixedHeights(topHeight: Double, bottomHeight: Double)
            = compile(VerticalSplitDoubleFixedLayout(topHeight, bottomHeight))
}

class CompiledVerticalSplitLayout(private val layout: Layout,
                                  private val verticalSplitLayout: VerticalSplitLayout){
    fun top() = layout.add { it, _ -> verticalSplitLayout.top(it) }
    fun bottom() = layout.add { it, _ -> verticalSplitLayout.bottom(it) }
}

interface VerticalSplitLayout{
    fun top(bounds: Bounds): Bounds
    fun bottom(bounds: Bounds): Bounds
}

class VerticalSplitDoubleScaledLayout(private val topScale: Double,
                                      private val topPad: Double,
                                      private val bottomScale: Double,
                                      private val bottomPad: Double) : VerticalSplitLayout{
    override fun top(bounds: Bounds) = bounds.scaleHeight(topScale, 0.0).resizeHeight(-topPad, 0.0)
    override fun bottom(bounds: Bounds) = bounds.scaleHeight(bottomScale, 1.0).resizeHeight(-bottomPad, 1.0)
}

class VerticalSplitFixedLeftLayout(private val topHeight: Double,
                                   private val sepHeight: Double) : VerticalSplitLayout{
    override fun top(bounds: Bounds) = bounds.setHeight(min(topHeight, bounds.area.height), 0.0)
    override fun bottom(bounds: Bounds) = bounds.setHeight(max(bounds.area.height-topHeight-sepHeight, 0.0), 1.0)
}

class VerticalSplitFixedRightLayout(private val bottomHeight: Double,
                                    private val sepHeight: Double) : VerticalSplitLayout{
    override fun top(bounds: Bounds) = bounds.setHeight(max(bounds.area.height-bottomHeight-sepHeight, 0.0), 0.0)
    override fun bottom(bounds: Bounds) = bounds.setHeight(min(bottomHeight, bounds.area.height), 1.0)
}

class VerticalSplitDoubleFixedLayout(private val topHeight: Double,
                                     private val bottomHeight: Double) : VerticalSplitLayout{
    override fun top(bounds: Bounds) = bounds.setHeight(min(topHeight, bounds.area.height), 0.0)
    override fun bottom(bounds: Bounds) = bounds.setHeight(min(bottomHeight, bounds.area.height), 1.0)
}
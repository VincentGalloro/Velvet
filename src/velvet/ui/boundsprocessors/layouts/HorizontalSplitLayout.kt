package velvet.ui.boundsprocessors.layouts

import velvet.util.types.spatial.Bounds
import java.lang.Double.max
import java.lang.Double.min

class HorizontalSplitFactory(private val layout: Layout){

    private fun compile(horizontalSplitLayout: HorizontalSplitLayout)
            = CompiledHorizontalSplitLayout(layout, horizontalSplitLayout)

    fun half(sepScale: Double = 0.0, sepPad: Double = 0.0)
            = compile(HorizontalSplitDoubleScaledLayout(0.5 - sepScale/2, sepPad/2, 0.5 - sepScale/2, sepPad/2))

    fun leftScaled(leftScale: Double, sepScale: Double = 0.0, sepPad: Double = 0.0)
            = compile(HorizontalSplitDoubleScaledLayout(leftScale, 0.0, 1 - leftScale - sepScale, sepPad))
    fun rightScaled(rightScale: Double, sepScale: Double = 0.0, sepPad: Double = 0.0)
            = compile(HorizontalSplitDoubleScaledLayout(1 - rightScale - sepScale, sepPad, rightScale, 0.0))

    fun leftWidth(leftWidth: Double, sepWidth: Double = 0.0)
            = compile(HorizontalSplitFixedLeftLayout(leftWidth, sepWidth))
    fun rightWidth(rightScale: Double, sepWidth: Double = 0.0)
            = compile(HorizontalSplitFixedRightLayout(rightScale, sepWidth))

    fun fixedWidths(leftWidth: Double, rightWidth: Double)
            = compile(HorizontalSplitDoubleFixedLayout(leftWidth, rightWidth))
}

class CompiledHorizontalSplitLayout(private val layout: Layout,
                                    private val horizontalSplitLayout: HorizontalSplitLayout){
    fun left() = layout.add { it, _ -> horizontalSplitLayout.left(it) }
    fun right() = layout.add { it, _ -> horizontalSplitLayout.right(it) }
}

interface HorizontalSplitLayout{
    fun left(bounds: Bounds): Bounds
    fun right(bounds: Bounds): Bounds
}

class HorizontalSplitDoubleScaledLayout(private val leftScale: Double,
                                        private val leftPad: Double,
                                        private val rightScale: Double,
                                        private val rightPad: Double) : HorizontalSplitLayout{
    override fun left(bounds: Bounds) = bounds.scaleWidth(leftScale, 0.0).resizeWidth(-leftPad, 0.0)
    override fun right(bounds: Bounds) = bounds.scaleWidth(rightScale, 1.0).resizeWidth(-rightPad, 1.0)
}

class HorizontalSplitFixedLeftLayout(private val leftWidth: Double,
                                     private val sepWidth: Double) : HorizontalSplitLayout{
    override fun left(bounds: Bounds) = bounds.setWidth(min(leftWidth, bounds.area.width), 0.0)
    override fun right(bounds: Bounds) = bounds.setWidth(max(bounds.area.width-leftWidth-sepWidth, 0.0), 1.0)
}

class HorizontalSplitFixedRightLayout(private val rightWidth: Double,
                                      private val sepWidth: Double) : HorizontalSplitLayout{
    override fun left(bounds: Bounds) = bounds.setWidth(max(bounds.area.width-rightWidth-sepWidth, 0.0), 0.0)
    override fun right(bounds: Bounds) = bounds.setWidth(min(rightWidth, bounds.area.width), 1.0)
}

class HorizontalSplitDoubleFixedLayout(private val leftWidth: Double,
                                       private val rightWidth: Double) : HorizontalSplitLayout{
    override fun left(bounds: Bounds) = bounds.setWidth(min(leftWidth, bounds.area.width), 0.0)
    override fun right(bounds: Bounds) = bounds.setWidth(min(rightWidth, bounds.area.width), 1.0)
}
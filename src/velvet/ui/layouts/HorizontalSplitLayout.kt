package velvet.ui.layouts

import velvet.structs.Bounds
import java.lang.Double.max
import java.lang.Double.min

class HorizontalSplitBuilder(private val layout: Layout){

    private fun wrapGenerator(horizontalSplitModifier: HorizontalSplitModifier) =
            HorizontalSplitLayoutGenerator(layout, horizontalSplitModifier)

    fun leftPercentage(scale: Double) =
            wrapGenerator(HorizontalSplitScaleLayout(scale))
    fun rightPercentage(scale: Double) =
            wrapGenerator(HorizontalSplitScaleLayout(1-scale))
    fun maxLeftMinRight(maxLeft: Double, minRight: Double) =
            wrapGenerator(HorizontalSplitMaxLeftMinRightLayout(maxLeft, minRight))
    fun maxRightMinLeft(maxRight: Double, minLeft: Double) =
            wrapGenerator(HorizontalSplitMaxRightMinLeftLayout(maxRight, minLeft))
}

class HorizontalSplitLayoutGenerator(private val layout: Layout,
                                     private val horizontalSplitModifier: HorizontalSplitModifier){
    fun left() = layout.addModifier(horizontalSplitModifier.left)
    fun right() = layout.addModifier(horizontalSplitModifier.right)
}

interface HorizontalSplitModifier{
    val left: (Bounds)->Bounds
    val right: (Bounds)->Bounds
}

class HorizontalSplitScaleLayout(private val scale: Double) : HorizontalSplitModifier {
    override val left: (Bounds)->Bounds = { it.scaleWidth(scale, 0.0) }
    override val right: (Bounds)->Bounds = { it.scaleWidth(1-scale, 1.0) }
}

class HorizontalSplitMaxLeftMinRightLayout(private val maxLeft: Double,
                                           private val minRight: Double) : HorizontalSplitModifier{
    private fun calculateLeftWidth(bounds: Bounds) = max(min(maxLeft, bounds.size.x-minRight), 0.0)
    override val left: (Bounds)->Bounds = { it.setWidth(calculateLeftWidth(it), 0.0) }
    override val right: (Bounds)->Bounds = { it.setWidth(it.size.x - calculateLeftWidth(it), 1.0) }
}

class HorizontalSplitMaxRightMinLeftLayout(private val maxRight: Double,
                                           private val minLeft: Double) : HorizontalSplitModifier{
    private fun calculateRightWidth(bounds: Bounds) = max(min(maxRight, bounds.size.x-minLeft), 0.0)
    override val left: (Bounds)->Bounds = { it.setWidth(it.size.x - calculateRightWidth(it), 0.0) }
    override val right: (Bounds)->Bounds = { it.setWidth(calculateRightWidth(it), 1.0) }
}
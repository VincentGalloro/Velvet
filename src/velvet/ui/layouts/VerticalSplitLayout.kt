package velvet.ui.layouts

import velvet.structs.Bounds
import java.lang.Double.max
import java.lang.Double.min

class VerticalSplitBuilder(private val layout: Layout){

    private fun wrapGenerator(verticalSplitModifier: VerticalSplitModifier) =
            VerticalSplitLayoutGenerator(layout, verticalSplitModifier)

    fun topPercentage(scale: Double) =
            wrapGenerator(VerticalSplitScaleLayout(scale))
    fun bottomPercentage(scale: Double) =
            wrapGenerator(VerticalSplitScaleLayout(1-scale))
    fun maxTopMinBottom(maxTop: Double, minBottom: Double) =
            wrapGenerator(VerticalSplitMaxTopMinBottomLayout(maxTop, minBottom))
    fun maxBottomMinTop(maxBottom: Double, minTop: Double) =
            wrapGenerator(VerticalSplitMaxBottomMinTopLayout(maxBottom, minTop))
}

class VerticalSplitLayoutGenerator(private val layout: Layout,
                                   private val verticalSplitModifier: VerticalSplitModifier){
    fun top() = layout.addModifier(verticalSplitModifier.top)
    fun bottom() = layout.addModifier(verticalSplitModifier.bottom)
}

interface VerticalSplitModifier{
    val top: (Bounds)->Bounds
    val bottom: (Bounds)->Bounds
}

class VerticalSplitScaleLayout(private val scale: Double) : VerticalSplitModifier {
    override val top: (Bounds)->Bounds = { it.scaleHeight(scale, 0.0) }
    override val bottom: (Bounds)->Bounds = { it.scaleHeight(1-scale, 1.0) }
}

class VerticalSplitMaxTopMinBottomLayout(private val maxTop: Double,
                                         private val minBottom: Double) : VerticalSplitModifier {
    private fun calculateTopHeight(bounds: Bounds) = max(min(maxTop, bounds.size.y-minBottom), 0.0)
    override val top: (Bounds)->Bounds = { it.setHeight(calculateTopHeight(it), 0.0) }
    override val bottom: (Bounds)->Bounds = { it.setHeight(it.size.y - calculateTopHeight(it), 1.0) }
}

class VerticalSplitMaxBottomMinTopLayout(private val maxBottom: Double,
                                         private val minTop: Double) : VerticalSplitModifier {
    private fun calculateBottomHeight(bounds: Bounds) = max(min(maxBottom, bounds.size.y-minTop), 0.0)
    override val top: (Bounds)->Bounds = { it.setHeight(it.size.y - calculateBottomHeight(it), 0.0) }
    override val bottom: (Bounds)->Bounds = { it.setHeight(calculateBottomHeight(it), 1.0) }
}
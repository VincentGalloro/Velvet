package velvet.ui.boundsprocessors.layouts

import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Vector
import velvet.ui.boundsprocessors.BoundsProcessor
import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.ui.boundsprocessors.smooth.actuators.impl.BoundsSwishActuator
import velvet.ui.velements.VElement

class Layout(val boundsProcessors: List<BoundsProcessor>): BoundsProcessor {

    companion object{
        fun new() = Layout(emptyList())
        fun empty() = Layout(emptyList())
        fun of(boundsProcessor: BoundsProcessor) = Layout(listOf(boundsProcessor))
    }

    override fun invoke(bounds: Bounds, index: Int) = boundsProcessors.fold(bounds){ b, f -> f(b, index) }

    fun add(boundsProcessor: BoundsProcessor) = Layout(boundsProcessors + boundsProcessor)

    fun fixRatio(vElement: VElement, anchor: Vector = Vector.HALF)
            = add { it, _ -> it.fixRatioElement(vElement, anchor) }
    fun fixRatio(ratio: Vector, anchor: Vector = Vector.HALF)
            = add { it, _ -> it.fixRatio(ratio, anchor) }

    fun horizontalSplit() = HorizontalSplitFactory(this)
    fun verticalSplit() = VerticalSplitFactory(this)

    fun columnList() = ColumnListLayoutFactory(this)
    fun rowList() = RowListLayoutFactory(this)

    fun track(boundsProcessor: BoundsProcessor,
              initial: Bounds? = null,
              boundsInitializer: BoundsProcessor = { it, _ -> it })
            = add(TrackLayout(boundsProcessor, initial, boundsInitializer))
    fun snapTrack(boundsProcessor: BoundsProcessor): Pair<Layout, SnapTrackLayout>{
        val snapTrackLayout = SnapTrackLayout(boundsProcessor)
        return add(snapTrackLayout) to snapTrackLayout
    }

    fun toggleable(boundsProcessor: BoundsProcessor,
                   enabled: Boolean = true): Pair<Layout, ToggleableLayout>{
        val toggleableLayout = ToggleableLayout(boundsProcessor, enabled)
        return add(toggleableLayout) to toggleableLayout
    }

    fun pad(amount: Vector, anchor: Vector) = add { it, _ -> it.resize(-amount, anchor) }
    fun pad(amount: Double, anchor: Vector) = add { it, _ -> it.resize(Vector(-amount), anchor) }
    fun padCenter(amount: Double) = add { it, _ -> it.resize(Vector(-amount), Vector.HALF) }
    fun padTop(amount: Double) = add { it, _ -> it.resizeHeight(-amount, 1.0) }
    fun padRight(amount: Double) = add { it, _ -> it.resizeWidth(-amount, 0.0) }
    fun padBottom(amount: Double) = add { it, _ -> it.resizeHeight(-amount, 0.0) }
    fun padLeft(amount: Double) = add { it, _ -> it.resizeWidth(-amount, 1.0) }

    fun scale(amount: Vector, anchor: Vector) = add { it, _ -> it.scale(amount, anchor) }
    fun scale(amount: Double, anchor: Vector) = add { it, _ -> it.scale(Vector(amount), anchor) }
    fun scaleCenter(amount: Double) = add { it, _ -> it.scale(Vector(amount), Vector.HALF) }
    fun scaleTop(amount: Double) = add { it, _ -> it.scaleHeight(amount, 1.0) }
    fun scaleRight(amount: Double) = add { it, _ -> it.scaleWidth(amount, 0.0) }
    fun scaleBottom(amount: Double) = add { it, _ -> it.scaleHeight(amount, 0.0) }
    fun scaleLeft(amount: Double) = add { it, _ -> it.scaleWidth(amount, 1.0) }
}
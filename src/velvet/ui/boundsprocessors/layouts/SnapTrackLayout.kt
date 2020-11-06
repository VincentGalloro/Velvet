package velvet.ui.boundsprocessors.layouts

import velvet.ui.boundsprocessors.BoundsProcessor
import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.ui.boundsprocessors.smooth.actuators.impl.BoundsSwishActuator
import velvet.util.types.spatial.Bounds

class SnapTrackLayout(private val boundsProcessor: BoundsProcessor) : BoundsProcessor {

    private var prevTarget: Bounds? = null
    private var bounds: Bounds = Bounds()
    private val actuator: Actuator<Bounds> = BoundsSwishActuator()

    override fun invoke(it: Bounds, index: Int): Bounds {
        val newTarget = boundsProcessor(it, index)
        prevTarget?.let {
            bounds = Bounds.fromCenterOfArea(
                    bounds.center + (newTarget.center - it.center),
                    (bounds.area.vector + (newTarget.area.vector - it.area.vector)).area)
        }
        prevTarget = newTarget
        bounds = actuator(bounds, newTarget)
        return bounds
    }

    fun snap(){ prevTarget = null }
}
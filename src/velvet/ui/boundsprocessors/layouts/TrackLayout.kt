package velvet.ui.boundsprocessors.layouts

import velvet.ui.boundsprocessors.BoundsProcessor
import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.ui.boundsprocessors.smooth.actuators.impl.BoundsSwishActuator
import velvet.util.types.spatial.Bounds

class TrackLayout(private val boundsProcessor: BoundsProcessor,
                  private var bounds: Bounds? = null,
                  private val boundsInitializer: BoundsProcessor = { it, _ -> it } ) : BoundsProcessor {

    private val actuator: Actuator<Bounds> = BoundsSwishActuator()

    override fun invoke(it: Bounds, index: Int): Bounds {
        val target = boundsProcessor(it, index)
        val next = bounds?.let { current -> actuator(current, target) } ?: boundsInitializer(target, index)
        bounds = next
        return next
    }
}
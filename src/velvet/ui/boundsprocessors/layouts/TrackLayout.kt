package velvet.ui.boundsprocessors.layouts

import velvet.ui.boundsprocessors.BoundsProcessor
import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.ui.boundsprocessors.smooth.actuators.impl.BoundsSwishActuator
import velvet.util.types.spatial.Bounds

class TrackLayout(private val boundsProcessor: BoundsProcessor,
                  private var bounds: Bounds? = null) : BoundsProcessor {

    private val actuator: Actuator<Bounds> = BoundsSwishActuator()

    override fun invoke(it: Bounds, index: Int)
            = actuator(bounds ?: it, boundsProcessor(it, index)).also { bounds = it }
}
package velvet.velements.container

import velvet.smooth.actuators.Actuator
import velvet.smooth.actuators.impl.BoundsSwishActuator
import velvet.structs.Bounds
import velvet.velements.VElement

interface BoundsContainer{

    companion object {

        fun of(bounds: Bounds) = object : BoundsContainer {
            override val bounds = bounds
            override fun update(vElement: VElement?) {}
        }

        fun trackingFromTo(start: Bounds,
                           target: Bounds,
                           actuator: Actuator<Bounds> = BoundsSwishActuator()) =
                object : BoundsContainer {
                    override var bounds = start
                    override fun update(vElement: VElement?) { bounds = actuator.step(bounds, target) }
                }

        fun tracking(boundsGenerator: (VElement?)->Bounds) = object : BoundsContainer{
            override var bounds = boundsGenerator(null)
            override fun update(vElement: VElement?) { bounds = boundsGenerator(vElement) }
        }
    }

    val bounds: Bounds

    fun update(vElement: VElement?)
}
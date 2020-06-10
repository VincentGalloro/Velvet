package velvet.ui.boundsprocessors.smooth.actuators.impl

import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.util.types.spatial.Vector

class VectorSwishActuator(private val speed: Double = 0.1) : Actuator<Vector> {

    override fun invoke(current: Vector, target: Vector) = current*(1-speed) + target*speed
}

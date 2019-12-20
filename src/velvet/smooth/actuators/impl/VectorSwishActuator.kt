package velvet.smooth.actuators.impl

import velvet.smooth.actuators.Actuator
import velvet.structs.Vector

class VectorSwishActuator(private val speed: Double = 0.1) : Actuator<Vector> {

    override fun step(current: Vector, target: Vector): Vector {
        return current*(1-speed) + target*speed
    }
}

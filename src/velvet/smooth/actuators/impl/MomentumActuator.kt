package velvet.smooth.actuators.impl

import velvet.smooth.actuators.Actuator
import velvet.structs.Vector

class MomentumActuator (private val acceleration: Double = 2.0,
                        private val friction: Double = 0.1) : Actuator<Vector> {

    var velocity: Vector = Vector()

    override fun step(current: Vector, target: Vector): Vector {
        val acc = Vector.unitVector(current.getAngle(target)) * acceleration
        val fri = -velocity*friction
        velocity += acc + fri

        return current + velocity
    }
}

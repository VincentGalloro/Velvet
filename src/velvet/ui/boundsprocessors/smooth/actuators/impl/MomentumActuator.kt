package velvet.ui.boundsprocessors.smooth.actuators.impl

import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.util.types.spatial.Vector

class MomentumActuator (private val acceleration: Double = 2.0,
                        private val friction: Double = 0.1) : Actuator<Vector> {

    private var velocity: Vector = Vector()

    override fun invoke(current: Vector, target: Vector): Vector {
        val acc = Vector.unitVector(current.angleTo(target)) * acceleration
        val fri = -velocity*friction
        velocity += acc + fri

        return current + velocity
    }
}

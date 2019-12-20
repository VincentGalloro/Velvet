package velvet.smooth.actuators.impl

import velvet.smooth.actuators.Actuator
import velvet.structs.Bounds
import velvet.structs.Vector

class BoundsSwishActuator(private val vectorActuator: Actuator<Vector> = VectorSwishActuator()) : Actuator<Bounds> {

    override fun step(current: Bounds, target: Bounds) =
            Bounds(vectorActuator.step(current.start, target.start), vectorActuator.step(current.end, target.end))
}
package velvet.smooth.actuators.impl

import velvet.smooth.actuators.Actuator
import velvet.structs.Bounds
import velvet.structs.Vector

class BoundsSwishActuator(private val vectorActuator: Actuator<Vector> = VectorSwishActuator(),
                          private val doubleActuator: Actuator<Double> = DoubleSwishActuator()) : Actuator<Bounds> {

    override fun step(current: Bounds, target: Bounds) =
            Bounds(vectorActuator.step(current.center, target.center),
                    vectorActuator.step(current.size, target.size),
                    doubleActuator.step(current.angle, target.angle))
}
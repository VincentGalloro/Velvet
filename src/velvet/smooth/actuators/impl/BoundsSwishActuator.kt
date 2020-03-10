package velvet.smooth.actuators.impl

import velvet.smooth.actuators.Actuator
import velvet.structs.Bounds
import velvet.structs.Vector

class BoundsSwishActuator(private val vectorActuator: Actuator<Vector> = VectorSwishActuator(),
                          private val doubleActuator: Actuator<Double> = DoubleSwishActuator()) : Actuator<Bounds> {

    override fun step(current: Bounds, target: Bounds) =
            current.setPos(vectorActuator.step(current.center, target.center))
                    .setSize(vectorActuator.step(current.size, target.size), Vector(0.5))
                    .setAngle(doubleActuator.step(current.angle, target.angle))
}
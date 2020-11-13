package velvet.ui.boundsprocessors.smooth.actuators.impl

import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Vector

class BoundsSwishActuator(private val vectorActuator: Actuator<Vector> = VectorSwishActuator(),
                          private val areaActuator: Actuator<Area> = AreaSwishActuator(),
                          private val doubleActuator: Actuator<Double> = DoubleSwishActuator()) : Actuator<Bounds> {

    companion object{

        fun speed(speed: Double) = BoundsSwishActuator(
                VectorSwishActuator(speed),
                AreaSwishActuator(speed),
                DoubleSwishActuator(speed)
        )
    }

    override fun invoke(current: Bounds, target: Bounds) = Bounds(
            vectorActuator(current.center, target.center),
            areaActuator(current.size, target.size),
            doubleActuator(current.angle, target.angle))
}
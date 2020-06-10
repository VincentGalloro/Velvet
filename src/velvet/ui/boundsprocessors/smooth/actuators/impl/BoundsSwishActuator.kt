package velvet.ui.boundsprocessors.smooth.actuators.impl

import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Vector

class BoundsSwishActuator(private val vectorActuator: Actuator<Vector> = VectorSwishActuator(),
                          private val areaActuator: Actuator<Area> = AreaSwishActuator(),
                          private val doubleActuator: Actuator<Double> = DoubleSwishActuator()) : Actuator<Bounds> {

    override fun invoke(current: Bounds, target: Bounds) =
            current.setCenter(vectorActuator(current.center, target.center))
                    .setArea(areaActuator(current.area, target.area), Vector(0.5))
                    .setAngle(doubleActuator(current.angle, target.angle))
}
package velvet.ui.boundsprocessors.smooth.actuators.impl

import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Size

class AreaSwishActuator(val speed: Double = 0.1) : Actuator<Area> {

    override fun invoke(current: Area, target: Area) = (current.vector*(1-speed) + target.vector*speed).area
}
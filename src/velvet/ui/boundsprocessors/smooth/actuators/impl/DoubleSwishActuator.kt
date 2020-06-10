package velvet.ui.boundsprocessors.smooth.actuators.impl

import velvet.ui.boundsprocessors.smooth.actuators.Actuator

class DoubleSwishActuator (private val speed: Double = 0.1) : Actuator<Double> {

    override fun invoke(current: Double, target: Double) = current * (1 - speed) + target * speed
}

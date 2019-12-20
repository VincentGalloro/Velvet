package velvet.smooth.actuators.impl

import velvet.smooth.actuators.Actuator

class DoubleSwishActuator (private val speed: Double = 0.1) : Actuator<Double> {

    override fun step(current: Double, target: Double): Double {
        return current * (1 - speed) + target * speed
    }
}

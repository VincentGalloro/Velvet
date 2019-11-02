package velvet.smooth.tracker.impl

import velvet.smooth.tracker.Tracker

class DoubleSwishTracker (private val speed: Double = 0.1) : Tracker<Double> {

    override fun step(current: Double, target: Double): Double {
        return current * (1 - speed) + target * speed
    }
}

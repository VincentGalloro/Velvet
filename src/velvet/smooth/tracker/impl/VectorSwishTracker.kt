package velvet.smooth.tracker.impl

import velvet.smooth.tracker.Tracker
import velvet.structs.Vector

class VectorSwishTracker(private val speed: Double = 0.1) : Tracker<Vector> {

    override fun step(current: Vector, target: Vector): Vector {
        return current.multiply(1 - speed).add(target.multiply(speed))
    }
}

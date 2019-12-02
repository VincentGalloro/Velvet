package velvet.smooth.tracker.impl

import velvet.smooth.tracker.Tracker
import velvet.structs.Vector

class MomentumTracker (private val acceleration: Double = 2.0,
                       private val friction: Double = 0.1) : Tracker<Vector> {

    var velocity: Vector = Vector()

    override fun step(current: Vector, target: Vector): Vector {
        val acc = Vector.unitVector(current.getAngle(target)) * acceleration
        val fri = -velocity*friction
        velocity += acc + fri

        return current + velocity
    }
}

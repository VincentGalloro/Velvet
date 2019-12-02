package velvet.velements.container

import velvet.smooth.tracker.Tracker
import velvet.smooth.tracker.impl.VectorSwishTracker
import velvet.structs.Bounds
import velvet.structs.Vector

class BoundsActuator (private val vContainer: VContainer,
                      private val vectorTracker: Tracker<Vector> = VectorSwishTracker()) {

    var targetBounds = Bounds()

    fun step() {
        vContainer.bounds = Bounds(
                vectorTracker.step(vContainer.bounds.start, targetBounds.start),
                vectorTracker.step(vContainer.bounds.end, targetBounds.end))
    }
}

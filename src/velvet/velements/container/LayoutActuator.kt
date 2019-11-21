package velvet.velements.container

import velvet.smooth.tracker.Tracker
import velvet.smooth.tracker.impl.DoubleSwishTracker
import velvet.smooth.tracker.impl.VectorSwishTracker
import velvet.structs.Vector

class LayoutActuator (private val vContainer: VContainer,
                      private val posTracker: Tracker<Vector> = VectorSwishTracker(),
                      private val sizeTracker: Tracker<Vector> = VectorSwishTracker(),
                      private val angleTracker: Tracker<Double> = DoubleSwishTracker()) {

    var targetLayout: ContainerLayout = ContainerLayout()

    fun step() {
        vContainer.containerLayout = ContainerLayout(
                posTracker.step(vContainer.containerLayout.pos, targetLayout.pos),
                sizeTracker.step(vContainer.containerLayout.size, targetLayout.size),
                angleTracker.step(vContainer.containerLayout.angle, targetLayout.angle))
    }
}

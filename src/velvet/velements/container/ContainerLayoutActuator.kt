package velvet.velements.container

import velvet.smooth.tracker.Tracker
import velvet.smooth.tracker.impl.DoubleSwishTracker
import velvet.smooth.tracker.impl.VectorSwishTracker
import velvet.structs.Vector

class ContainerLayoutActuator (val vContainer: VContainer,
                               val posTracker: Tracker<Vector> = VectorSwishTracker(),
                               val sizeTracker: Tracker<Vector> = VectorSwishTracker(),
                               val originTracker: Tracker<Vector> = VectorSwishTracker(),
                               val angleTracker: Tracker<Double> = DoubleSwishTracker()) {

    var targetLayout: ContainerLayout = ContainerLayout()

    fun step() {
        vContainer.containerLayout = ContainerLayout(
                posTracker.step(vContainer.containerLayout.pos, targetLayout.pos),
                sizeTracker.step(vContainer.containerLayout.size, targetLayout.size),
                originTracker.step(vContainer.containerLayout.origin, targetLayout.origin),
                angleTracker.step(vContainer.containerLayout.angle, targetLayout.angle))
    }
}

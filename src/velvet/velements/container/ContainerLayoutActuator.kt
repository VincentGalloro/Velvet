package velvet.velements.container

import velvet.smooth.tracker.Tracker
import velvet.smooth.tracker.impl.DoubleSwishTracker
import velvet.smooth.tracker.impl.VectorSwishTracker
import velvet.structs.Vector

class ContainerLayoutActuator (val layoutSupplier: ()->ContainerLayout,
                               val layoutConsumer: (ContainerLayout)->Unit,
                               val posTracker: Tracker<Vector> = VectorSwishTracker(),
                               val sizeTracker: Tracker<Vector> = VectorSwishTracker(),
                               val originTracker: Tracker<Vector> = VectorSwishTracker(),
                               val angleTracker: Tracker<Double> = DoubleSwishTracker()) {

    var targetLayout: ContainerLayout = ContainerLayout()

    fun step() {
        val layout = layoutSupplier()
        layoutConsumer(ContainerLayout(
                posTracker.step(layout.pos, targetLayout.pos),
                sizeTracker.step(layout.size, targetLayout.size),
                originTracker.step(layout.origin, targetLayout.origin),
                angleTracker.step(layout.angle, targetLayout.angle)))
    }
}

package velvet.game

import velvet.main.VGraphics
import velvet.ui.UINode
import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.ui.boundsprocessors.smooth.actuators.impl.BoundsSwishActuator
import velvet.ui.components.BasicComponent
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Size
import velvet.util.types.spatial.Vector

class Camera(val windowSize: Size) : BasicComponent(){

    val windowBounds = Bounds.fromStartOfSize(Vector(), windowSize.toArea())
    var bounds = windowBounds
    var targetBounds: Bounds? = null
    var boundsActuator: Actuator<Bounds> = BoundsSwishActuator()
    var cameraBoundsRenderMode: CameraBoundsRenderMode = PadToFitRenderMode()

    fun fromScreenPos(pos: Vector) = bounds.getPos(windowBounds.getAnchor(pos))
    fun toScreenPos(pos: Vector) = windowBounds.getPos(bounds.getAnchor(pos))

    override fun preUpdate(uiNode: UINode) {
        targetBounds?.let { bounds = boundsActuator(bounds, cameraBoundsRenderMode(it, windowSize)) }
    }

    override fun preRender(uiNode: UINode, g: VGraphics) {
        g.save()
        g.scale(windowSize.vector / bounds.size)
        g.translate(-bounds.getPos(Vector()))
        g.rotate(-bounds.angle, bounds.getPos(Vector()))
    }

    override fun postRender(uiNode: UINode, g: VGraphics) {
        g.reset()
    }
}
package velvet.io.hardware

import velvet.util.types.spatial.Vector
import java.awt.event.*

class Mouse(private val inputEventLogger: InputEventLogger)
    : MouseListener, MouseMotionListener, MouseWheelListener {

    override fun mousePressed(e: MouseEvent) {
        inputEventLogger.add(MouseButtonPressedEvent(e.button))
    }
    override fun mouseReleased(e: MouseEvent) {
        inputEventLogger.add(MouseButtonReleasedEvent(e.button))
    }

    override fun mouseDragged(e: MouseEvent) {
        inputEventLogger.add(MouseMovedEvent(Vector(e.x, e.y)))
    }
    override fun mouseMoved(e: MouseEvent) {
        inputEventLogger.add(MouseMovedEvent(Vector(e.x, e.y)))
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        inputEventLogger.add(MouseWheelScrolledEvent(e.wheelRotation))
    }

    override fun mouseClicked(e: MouseEvent) {}
    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseExited(e: MouseEvent) {}
}
package velvet.io.hardware

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class Keyboard(private val inputEventLogger: InputEventLogger) : KeyListener {

    override fun keyTyped(e: KeyEvent) {
        inputEventLogger.add(CharTypedEvent(e.keyChar))
    }

    override fun keyPressed(e: KeyEvent) {
        inputEventLogger.add(KeyPressedEvent(e.keyCode))
    }

    override fun keyReleased(e: KeyEvent) {
        inputEventLogger.add(KeyReleasedEvent(e.keyCode))
    }
}
package velvet.ui.events

import velvet.io.hardware.InputEventListener
import velvet.io.hardware.InputEventListenerImpl

class UIEventListener : InputEventListener by InputEventListenerImpl(){

    private var state = UIInputState.new()

    var onHoverStart: ((UIInputState) -> Unit)? = null
    var onHoverEnd: ((UIInputState) -> Unit)? = null

    var onFocusStart: ((UIInputState) -> Unit)? = null
    var onFocusEnd: ((UIInputState) -> Unit)? = null

    fun handleUiEvent(uiEvent: UIEvent): Unit?{
        state = uiEvent.updateInputState(state)
        return uiEvent.invokeListener(this, state)
    }

    fun isHoverable() = listOf(onHoverStart, onHoverEnd, onMouseWheelScrolled, onMouseMoved,
            onMouseButtonPressed, onMouseButtonReleased, onMouseWheelScrolled).any { it != null }
    fun isFocusable() = listOf(onFocusStart, onFocusEnd,
            onKeyPressed, onKeyReleased, onCharTyped).any { it != null }
}
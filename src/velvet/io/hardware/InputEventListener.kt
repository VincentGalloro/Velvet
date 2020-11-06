package velvet.io.hardware

interface InputEventListener {

    var onMouseButtonPressed: ((MouseButtonPressedEvent, InputState) -> Unit)?
    var onMouseButtonReleased: ((MouseButtonReleasedEvent, InputState) -> Unit)?
    var onMouseWheelScrolled: ((MouseWheelScrolledEvent, InputState) -> Unit)?
    var onMouseMoved: ((MouseMovedEvent, InputState) -> Unit)?

    var onKeyPressed: ((KeyPressedEvent, InputState) -> Unit)?
    var onKeyReleased: ((KeyReleasedEvent, InputState) -> Unit)?
    var onCharTyped: ((CharTypedEvent, InputState) -> Unit)?

    fun handleInputEvent(inputEvent: InputEvent, inputState: InputState): Unit?
            = inputEvent.invokeListener(this, inputState)
}

class InputEventListenerImpl : InputEventListener{

    override var onMouseButtonPressed: ((MouseButtonPressedEvent, InputState) -> Unit)? = null
    override var onMouseButtonReleased: ((MouseButtonReleasedEvent, InputState) -> Unit)? = null
    override var onMouseWheelScrolled: ((MouseWheelScrolledEvent, InputState) -> Unit)? = null
    override var onMouseMoved: ((MouseMovedEvent, InputState) -> Unit)? = null

    override var onKeyPressed: ((KeyPressedEvent, InputState) -> Unit)? = null
    override var onKeyReleased: ((KeyReleasedEvent, InputState) -> Unit)? = null
    override var onCharTyped: ((CharTypedEvent, InputState) -> Unit)? = null
}
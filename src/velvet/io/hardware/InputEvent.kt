package velvet.io.hardware

import velvet.util.types.spatial.Vector

interface InputEvent{

    fun updateInputState(inputState: InputState): InputState
    fun invokeListener(inputEventListener: InputEventListener, inputState: InputState): Unit?
}

data class MouseButtonPressedEvent(val button: Int) : InputEvent{

    override fun updateInputState(inputState: InputState)
            = inputState.copy(mouseButtonsDown = inputState.mouseButtonsDown + button)
    override fun invokeListener(inputEventListener: InputEventListener, inputState: InputState)
            = inputEventListener.onMouseButtonPressed?.invoke(this, inputState)
}

data class MouseButtonReleasedEvent(val button: Int) : InputEvent{

    override fun updateInputState(inputState: InputState)
            = inputState.copy(mouseButtonsDown = inputState.mouseButtonsDown - button)
    override fun invokeListener(inputEventListener: InputEventListener, inputState: InputState)
            = inputEventListener.onMouseButtonReleased?.invoke(this, inputState)
}

data class MouseWheelScrolledEvent(val amount: Int) : InputEvent{

    override fun updateInputState(inputState: InputState) = inputState
    override fun invokeListener(inputEventListener: InputEventListener, inputState: InputState)
            = inputEventListener.onMouseWheelScrolled?.invoke(this, inputState)
}

data class MouseMovedEvent(val pos: Vector) : InputEvent{

    override fun updateInputState(inputState: InputState)
            = inputState.copy(mousePos = pos)
    override fun invokeListener(inputEventListener: InputEventListener, inputState: InputState)
            = inputEventListener.onMouseMoved?.invoke(this, inputState)
}

data class KeyPressedEvent(val code: Int) : InputEvent{

    override fun updateInputState(inputState: InputState)
            = inputState.copy(keysDown = inputState.keysDown + code)
    override fun invokeListener(inputEventListener: InputEventListener, inputState: InputState)
            = inputEventListener.onKeyPressed?.invoke(this, inputState)
}

data class KeyReleasedEvent(val code: Int) : InputEvent{

    override fun updateInputState(inputState: InputState)
            = inputState.copy(keysDown = inputState.keysDown - code)
    override fun invokeListener(inputEventListener: InputEventListener, inputState: InputState)
            = inputEventListener.onKeyReleased?.invoke(this, inputState)
}

data class CharTypedEvent(val char: Char) : InputEvent{

    override fun updateInputState(inputState: InputState) = inputState
    override fun invokeListener(inputEventListener: InputEventListener, inputState: InputState)
            = inputEventListener.onCharTyped?.invoke(this, inputState)
}
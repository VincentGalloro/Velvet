package velvet.io.hardware

interface InputEventStream {

    val state: InputState
    val events: List<InputEvent>

    fun feedToListener(inputEventListener: InputEventListener){
        var currentState = state
        events.forEach { event ->
            currentState = event.updateInputState(currentState)
            inputEventListener.handleInputEvent(event, currentState)
        }
    }
}
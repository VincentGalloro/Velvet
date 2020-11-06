package velvet.io.hardware

import velvet.multithreading.SynchronizedQueue

class InputEventLogger : InputEventStream{

    private val eventBuffer: SynchronizedQueue<InputEvent> = SynchronizedQueue()
    override var state: InputState = InputState.new()
    override var events: List<InputEvent> = listOf()

    fun nextFrame(){
        events = eventBuffer.popAll()
        state = events.fold(state){ s, e -> e.updateInputState(s) }
    }

    fun add(inputEvent: InputEvent) = eventBuffer.add(inputEvent)

}
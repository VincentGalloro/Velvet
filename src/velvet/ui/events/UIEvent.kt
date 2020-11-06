package velvet.ui.events

interface UIEvent {

    fun updateInputState(uiInputState: UIInputState): UIInputState
    fun invokeListener(uiEventListener: UIEventListener, uiInputState: UIInputState): Unit?
}

class HoverStartEvent : UIEvent{
    override fun updateInputState(uiInputState: UIInputState)
            = uiInputState.copy(hovered = true)
    override fun invokeListener(uiEventListener: UIEventListener, uiInputState: UIInputState)
            = uiEventListener.onHoverStart?.invoke(uiInputState)
}

class HoverEndEvent : UIEvent{
    override fun updateInputState(uiInputState: UIInputState)
            = uiInputState.copy(hovered = false)
    override fun invokeListener(uiEventListener: UIEventListener, uiInputState: UIInputState)
            = uiEventListener.onHoverEnd?.invoke(uiInputState)
}

class FocusStartEvent : UIEvent{
    override fun updateInputState(uiInputState: UIInputState)
            = uiInputState.copy(focused = true)
    override fun invokeListener(uiEventListener: UIEventListener, uiInputState: UIInputState)
            = uiEventListener.onFocusStart?.invoke(uiInputState)
}

class FocusEndEvent : UIEvent{
    override fun updateInputState(uiInputState: UIInputState)
            = uiInputState.copy(focused = false)
    override fun invokeListener(uiEventListener: UIEventListener, uiInputState: UIInputState)
            = uiEventListener.onFocusEnd?.invoke(uiInputState)
}
package velvet.velements.interact

import velvet.main.Keyboard
import velvet.main.Mouse
import velvet.velements.container.VContainer

class UIEventHandler(private val mouse: Mouse, private val keyboard: Keyboard) {

    val containers: MutableList<VContainer> = ArrayList()

    var hover: VContainer? = null
    var focus: VContainer? = null

    fun switchFocus(container: VContainer?){
        if(focus != container) {
            focus?.uiEventListener?.onFocusEnd?.invoke()
            focus = container
            focus?.uiEventListener?.onFocusStart?.invoke()
        }
    }

    fun switchHover(container: VContainer?){
        if (hover != container) {
            hover?.uiEventListener?.onHoverEnd?.invoke()
            hover = container
            hover?.uiEventListener?.onHoverStart?.invoke()
        }
    }

    fun update(){
        if(mouse.isPressed(Mouse.LEFT)){
            hover?.uiEventListener?.onMousePress?.invoke()
            switchFocus(hover)
        }

        if(mouse.isReleased(Mouse.LEFT)){
            hover?.uiEventListener?.onMouseRelease?.invoke()
        }

        if(mouse.isDown(Mouse.LEFT)){
            hover?.uiEventListener?.onMouseDrag?.invoke(mouse.pos)
        }
        else{
            switchHover(containers.find { it.bounds.contains(mouse.pos) && it.vElement!=null && !it.disabled })

            hover?.uiEventListener?.onMouseHover?.invoke(mouse.pos)
        }

        focus?.let { focus ->
            keyboard.pressedLog.forEach { focus.uiEventListener.onKeyPressed?.invoke(it) }
            keyboard.releasedLog.forEach { focus.uiEventListener.onKeyReleased?.invoke(it) }
            keyboard.textTyped.forEach { focus.uiEventListener.onCharTyped?.invoke(it) }
        }
    }
}
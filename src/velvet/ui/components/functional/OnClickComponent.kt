package velvet.ui.components.functional

import velvet.ui.components.BasicComponent
import java.awt.event.MouseEvent

class OnClickComponent(private val button: Int,
                       private val onClick: ()->Unit) : BasicComponent(){

    companion object{
        fun left(onClick: () -> Unit) = OnClickComponent(MouseEvent.BUTTON1, onClick)
        fun right(onClick: () -> Unit) = OnClickComponent(MouseEvent.BUTTON3, onClick)
    }

    init{
        uiEventListener.onMouseButtonPressed = { e, _ ->
            if(e.button == button) onClick()
        }
    }
}
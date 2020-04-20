package velvet.ui.premade.components

import velvet.structs.VColor
import velvet.ui.UIEventListener
import velvet.ui.vcontainer.velements.SquareElement

class SquareHoverSelectComponent(squareElement: SquareElement) : UIComponent {

    override val uiEventListener = UIEventListener()

    init{
        var focused = false
        uiEventListener.onFocusStart = {
            focused = true
            squareElement.outlineColor = VColor(150, 200, 200)
        }
        uiEventListener.onFocusEnd = {
            focused = false
            squareElement.outlineColor = VColor.BLACK
        }

        uiEventListener.onHoverStart = {
            if(!focused) {
                squareElement.outlineColor = VColor(150, 150, 150)
            }
        }
        uiEventListener.onHoverEnd = {
            if(!focused) {
                squareElement.outlineColor = VColor.BLACK
            }
        }
    }
}
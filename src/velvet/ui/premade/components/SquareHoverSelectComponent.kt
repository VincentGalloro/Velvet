package velvet.ui.premade.components

import velvet.util.types.VColor
import velvet.ui.vcontainer.velements.SquareElement

class SquareHoverSelectComponent(squareElement: SquareElement) : BasicComponent() {

    private var focused = false

    init{
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
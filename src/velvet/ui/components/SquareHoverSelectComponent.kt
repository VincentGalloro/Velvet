package velvet.ui.components

import velvet.util.types.VColor
import velvet.ui.velements.SquareElement

class SquareHoverSelectComponent(squareElement: SquareElement) : BasicComponent() {

    init{
        uiEventListener.onFocusStart = {
            squareElement.outlineColor = VColor(150, 200, 200)
        }
        uiEventListener.onFocusEnd = {
            squareElement.outlineColor = VColor.BLACK
        }

        uiEventListener.onHoverStart = {
            if(!it.focused) {
                squareElement.outlineColor = VColor(150, 150, 150)
            }
        }
        uiEventListener.onHoverEnd = {
            if(!it.focused) {
                squareElement.outlineColor = VColor.BLACK
            }
        }
    }
}
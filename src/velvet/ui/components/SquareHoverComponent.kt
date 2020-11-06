package velvet.ui.components

import velvet.util.types.VColor
import velvet.ui.velements.SquareElement

class SquareHoverComponent(val squareElement: SquareElement) : BasicComponent(){

    init {
        uiEventListener.onHoverStart = { squareElement.outlineColor = VColor(150, 150, 150) }
        uiEventListener.onHoverEnd = { squareElement.outlineColor = VColor.BLACK }
    }
}
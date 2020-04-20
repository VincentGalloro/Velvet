package velvet.ui.premade.components

import velvet.structs.VColor
import velvet.ui.UIEventListener
import velvet.ui.vcontainer.velements.SquareElement

class SquareHoverComponent(val squareElement: SquareElement) : UIComponent {

    override val uiEventListener = UIEventListener()

    init {
        uiEventListener.onHoverStart = { squareElement.outlineColor = VColor(150, 150, 150) }
        uiEventListener.onHoverEnd = { squareElement.outlineColor = VColor.BLACK }
    }
}
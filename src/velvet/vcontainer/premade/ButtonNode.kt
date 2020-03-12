package velvet.vcontainer.premade

import velvet.structs.VColor
import velvet.vcontainer.interact.UINode

class ButtonNode(val outlinedTextNode: OutlinedTextNode) : UINode by outlinedTextNode{

    init{
        uiEventListener.onHoverStart = { outlinedTextNode.squareElement.outlineColor = VColor(150, 150, 150) }
        uiEventListener.onHoverEnd = { outlinedTextNode.squareElement.outlineColor = VColor.BLACK }
    }
}
package velvet.vcontainer.premade

import velvet.vcontainer.interact.UINode
import java.awt.Color

class ButtonNode(val outlinedTextNode: OutlinedTextNode) : UINode by outlinedTextNode{

    init{
        uiEventListener.onHoverStart = { outlinedTextNode.squareElement.outlineColor = Color(150, 150, 150) }
        uiEventListener.onHoverEnd = { outlinedTextNode.squareElement.outlineColor = Color.BLACK }
    }
}
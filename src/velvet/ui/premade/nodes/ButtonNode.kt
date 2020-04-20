package velvet.ui.premade.nodes

import velvet.ui.UINode
import velvet.ui.premade.components.SquareHoverComponent

class ButtonNode(text: String){

    val outlinedTextNode = OutlinedTextNode(text)
    val uiNode: UINode
        get() = outlinedTextNode.uiNode

    init{
        uiNode.uiComponents.add(SquareHoverComponent(outlinedTextNode.squareElement))
    }
}
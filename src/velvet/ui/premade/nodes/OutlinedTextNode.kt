package velvet.ui.premade.nodes

import velvet.structs.Vector
import velvet.ui.UINode
import velvet.ui.vcontainer.velements.SquareElement
import velvet.ui.vcontainer.velements.TextElement

class OutlinedTextNode (text: String, textScaling: Vector = Vector(0.8)) {

    val uiNode = UINode()

    val squareElement = SquareElement()
    val textElement = TextElement(text)

    val squareContainer = squareElement.createContainer()
    val textContainer = textElement.createContainer()

    init{
        uiNode.addContainer(squareContainer){ uiNode.bounds }
        uiNode.addContainer(textContainer){
            uiNode.bounds.scale(textScaling, Vector.HALF).fixRatioElement(textElement)
        }
    }
}
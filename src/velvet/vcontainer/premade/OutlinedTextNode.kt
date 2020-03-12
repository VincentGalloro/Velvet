package velvet.vcontainer.premade

import velvet.structs.Vector
import velvet.vcontainer.interact.TrackedVContainer
import velvet.vcontainer.interact.UINode
import velvet.vcontainer.interact.UINodeImpl
import velvet.vcontainer.velement.SquareElement
import velvet.vcontainer.velement.TextElement

class OutlinedTextNode(text: String,
                       textScaling: Vector = Vector(0.8)) : UINode by UINodeImpl(){

    val squareElement = SquareElement()
    val textElement = TextElement(text)

    var text: String
        get() = textElement.text
        set(value){ textElement.text = value }

    init{
        containers.add(TrackedVContainer(squareElement){ bounds })
        containers.add(TrackedVContainer(textElement, true) {
            bounds.scale(textScaling, Vector(0.5))
        })
    }
}
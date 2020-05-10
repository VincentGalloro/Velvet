package velvet.ui.premade.nodes

import velvet.structs.Vector
import velvet.ui.UINode
import velvet.ui.vcontainer.velements.SquareElement
import velvet.ui.vcontainer.velements.TextElement

class OutlinedTextNode (_text: String, textScaling: Vector = Vector(0.8)) : UINode(){

    val squareElement = SquareElement()
    val textElement = TextElement(_text)

    var text: String
        get() = textElement.text
        set(value) { textElement.text = value }

    val squareContainer = squareElement.createContainer()
    val textContainer = textElement.createContainer()

    init{
        add(squareContainer){ bounds }
        add(textContainer){
            bounds.scale(textScaling, Vector.HALF).fixRatioElement(textElement)
        }
    }
}
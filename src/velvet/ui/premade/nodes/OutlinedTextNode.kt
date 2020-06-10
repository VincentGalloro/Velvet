package velvet.ui.premade.nodes

import velvet.ui.UINode
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.premade.components.TextControllerComponent
import velvet.ui.vcontainer.velements.SquareElement
import velvet.ui.vcontainer.velements.TextElement

class OutlinedTextNode (_text: String, textScaling: Double = 0.8) : UINode(){

    val squareElement = SquareElement()
    val textElement = TextElement(_text)

    val textNode = basic(textElement)

    var text: String
        get() = textElement.text
        set(value) { textElement.text = value }

    init{
        add(squareElement)
        add(textNode, Layout.new().scaleCenter(textScaling).fixRatio(textElement))
    }

    fun createTextController() = TextControllerComponent(textNode, textElement).also { add(it) }
}
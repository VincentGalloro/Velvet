package velvet.ui.premade

import velvet.ui.UINode
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.components.functional.TextControllerComponent
import velvet.ui.velements.SquareElement
import velvet.ui.velements.TextElement

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
package velvet.ui.premade.nodes

import velvet.structs.VColor
import velvet.structs.Vector
import velvet.ui.vcontainer.velements.SquareElement
import velvet.ui.UINode
import velvet.ui.premade.components.SquareHoverSelectComponent
import velvet.ui.vcontainer.velements.TextElement

class InputFieldNode(promptText: String, defaultInput: String = ""){

    val uiNode = UINode()

    val squareElement = SquareElement(outlineThickness = 4.0, rounding = 20.0)
    val promptElement = TextElement(promptText, _color = VColor(200, 200, 200))
    val inputElement = TextElement(defaultInput).apply { setFontResolution(40) }

    val squareContainer = squareElement.createContainer()
    val promptContainer = promptElement.createContainer()
    val inputContainer = inputElement.createContainer()

    init {
        uiNode.addContainer(squareContainer){ uiNode.bounds }
        uiNode.addContainer(promptContainer) {
            uiNode.bounds.scale(Vector(0.3, 1.0), Vector(0))
                    .scale(Vector(0.8), Vector(0.5))
                    .fixRatioElement(promptElement)
        }
        uiNode.addContainer(inputContainer) {
            uiNode.bounds.scale(Vector(0.7, 1.0), Vector(1))
                    .scale(Vector(0.9), Vector(0.5))
                    .fixRatioElement(inputElement, Vector(0.0, 0.5))
        }

        uiNode.uiComponents.add(SquareHoverSelectComponent(squareElement))
    }
}
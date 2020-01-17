package velvet.velements.container.premade

import velvet.structs.Vector
import velvet.velements.container.VContainer
import velvet.velements.impl.SquareElement
import velvet.velements.impl.TextElement
import velvet.velements.interact.TextController
import velvet.velements.interact.UIEventHandler
import java.awt.Color

class InputField(uiEventHandler: UIEventHandler,
                 promptText: String,
                 defaultInput: String = ""): VContainer() {

    private val outlineElement = SquareElement(outlineThickness = 4.0, rounding = 20.0)
    private val promptElement = TextElement(promptText, _color = Color(200,200,200))
    private val inputTextElement = TextElement(defaultInput, fontResolution = 40)

    val textController = TextController(inputTextElement)

    var text: String
        get() = inputTextElement.text
        set(value){ inputTextElement.text = value }

    init {
        vElement = outlineElement
        subContainers.add(VContainer(promptElement) {
            //promptContainer
            bounds.scale(Vector(0.3, 1.0), Vector(0))
                    .scale(Vector(0.8))
                    .fixRatioElement(it.vElement, Vector(1.0, 0.5))
        })
        subContainers.add(VContainer(inputTextElement) {
            //inputContainer
            bounds.scale(Vector(0.7, 1.0), Vector(1))
                    .scale(Vector(0.9))
                    .fixRatioElement(it.vElement, Vector(0.0, 0.5))
        })

        uiEventHandler.containers.add(this)


        uiEventListener.onCharTyped = textController::onCharTyped
        uiEventListener.onKeyPressed = textController::onKeyPressed

        var focused = false
        uiEventListener.onFocusStart = {
            focused = true
            outlineElement.outlineColor = Color(150, 200, 200)
        }
        uiEventListener.onFocusEnd = {
            focused = false
            outlineElement.outlineColor = Color.BLACK
        }

        uiEventListener.onHoverStart = {
            if(!focused) {
                outlineElement.outlineColor = Color(150, 150, 150)
            }
        }
        uiEventListener.onHoverEnd = {
            if(!focused) {
                outlineElement.outlineColor = Color.BLACK
            }
        }
    }
}
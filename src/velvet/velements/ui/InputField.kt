package velvet.velements.ui

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.velements.container.VContainer
import velvet.velements.impl.SquareElement
import velvet.velements.impl.TextElement
import velvet.velements.interact.UIEventHandler
import java.awt.Color

class InputField(uiEventHandler: UIEventHandler,
                 promptText: String,
                 defaultInput: String = "") {

    private val outlineElement = SquareElement(outlineThickness = 4.0, rounding = 20.0)
    private val promptElement = TextElement(promptText, _color = Color(200,200,200))
    private val inputTextElement = TextElement(defaultInput, fontResolution = 40)

    val container = VContainer(outlineElement)
    private val promptContainer = VContainer(promptElement)
    private val inputContainer = VContainer(inputTextElement)

    val textController = TextController(inputTextElement)

    var text: String
        get() = inputTextElement.text
        set(value){ inputTextElement.text = value }

    init {
        uiEventHandler.containers.add(container)

        container.uiEventListener.onCharTyped = textController::onCharTyped
        container.uiEventListener.onKeyPressed = textController::onKeyPressed

        container.uiEventListener.onFocusStart = { outlineElement.outlineColor = Color(150, 150, 150) }
        container.uiEventListener.onFocusEnd = { outlineElement.outlineColor = Color.BLACK }
    }

    fun update(){
        if(container.disabled){ return; }
        promptContainer.bounds = container.bounds
                .scale(Vector(0.3,1.0), Vector(0.0,0.5))
                .fixRatio(promptContainer.vElement?.size ?: Vector(1), Vector(0.5))
                .scale(Vector(0.8), Vector(0.5))
        inputContainer.bounds = container.bounds
                .scale(Vector(0.7,1.0), Vector(1.0,0.5))
                .fixRatio(inputContainer.vElement?.size ?: Vector(1), Vector(0.0, 0.5))
                .scale(Vector(0.9), Vector(0.0, 0.5))
    }

    fun render(g: VGraphics){
        if(container.disabled){ return; }
        container.render(g)
        promptContainer.render(g)
        inputContainer.render(g)
    }
}
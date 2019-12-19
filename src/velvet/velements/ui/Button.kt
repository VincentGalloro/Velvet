package velvet.velements.ui

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.velements.container.VContainer
import velvet.velements.impl.SquareElement
import velvet.velements.impl.TextElement
import velvet.velements.interact.UIEventHandler
import java.awt.Color

class Button(uiEventHandler: UIEventHandler,
             text: String) {

    private val outlineElement = SquareElement(outlineThickness = 3.0, rounding = 20.0)
    private val textElement = TextElement(text, fontResolution = 40)

    val container = VContainer(outlineElement)
    private val textContainer = VContainer(textElement)

    init{
        uiEventHandler.containers.add(container)

        container.uiEventListener.onHoverStart = { outlineElement.outlineColor = Color(150, 150, 150) }
        container.uiEventListener.onHoverEnd = { outlineElement.outlineColor = Color.BLACK }
    }

    fun update(){
        if(container.disabled){ return; }

        textContainer.bounds = container.bounds
                .fixRatio(textContainer.vElement?.size ?: Vector(1), Vector(0.5))
                .scale(Vector(0.9), Vector(0.5))
    }

    fun render(g: VGraphics){
        if(container.disabled){ return; }

        container.render(g)
        textContainer.render(g)
    }
}
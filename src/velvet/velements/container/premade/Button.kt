package velvet.velements.container.premade

import velvet.structs.Vector
import velvet.velements.container.premade.BasicOutlinedTextContainer
import velvet.velements.impl.SquareElement
import velvet.velements.interact.UIEventHandler
import java.awt.Color

class Button(uiEventHandler: UIEventHandler,
             text: String) : BasicOutlinedTextContainer(text,
        Vector(0.9),
        SquareElement(outlineThickness = 3.0, rounding = 20.0)) {

    init{
        uiEventHandler.containers.add(this)

        uiEventListener.onHoverStart = { outlineElement.outlineColor = Color(150, 150, 150) }
        uiEventListener.onHoverEnd = { outlineElement.outlineColor = Color.BLACK }
    }
}
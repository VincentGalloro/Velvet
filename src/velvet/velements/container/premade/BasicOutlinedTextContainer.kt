package velvet.velements.container.premade

import velvet.structs.Vector
import velvet.velements.container.BoundsContainer
import velvet.velements.container.VContainer
import velvet.velements.impl.SquareElement
import velvet.velements.impl.TextElement

open class BasicOutlinedTextContainer(text: String,
                                      textScaling: Vector = Vector(0.8),
                                      val outlineElement: SquareElement = SquareElement(),
                                      val textElement: TextElement = TextElement(text))
    : VContainer(outlineElement) {

    init{
        subContainers.add(VContainer(textElement, BoundsContainer.tracking {
            //nameContainer
            bounds.scale(textScaling).fixRatioElement(it)
        }))
    }
}
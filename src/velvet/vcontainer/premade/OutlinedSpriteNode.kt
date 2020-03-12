package velvet.vcontainer.premade

import velvet.main.game.graphics.Sprite
import velvet.vcontainer.interact.TrackedVContainer
import velvet.vcontainer.interact.UINode
import velvet.vcontainer.interact.UINodeImpl
import velvet.vcontainer.velement.SpriteElement
import velvet.vcontainer.velement.SquareElement

class OutlinedSpriteNode : UINode by UINodeImpl() {

    val squareElement = SquareElement()
    val spriteElement = SpriteElement()

    var sprite: Sprite?
        get() = spriteElement.sprite
        set(value){ spriteElement.sprite = value }

    init{
        containers.add(TrackedVContainer(squareElement){ bounds })
        containers.add(TrackedVContainer(spriteElement, true) { bounds })
    }
}
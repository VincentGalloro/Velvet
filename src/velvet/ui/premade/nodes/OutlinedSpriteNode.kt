package velvet.ui.premade.nodes

import velvet.main.game.graphics.Sprite
import velvet.ui.UINode
import velvet.ui.vcontainer.velements.SpriteElement
import velvet.ui.vcontainer.velements.SquareElement

class OutlinedSpriteNode {

    val uiNode = UINode()

    val squareElement = SquareElement()
    val spriteElement = SpriteElement()

    val squareContainer = squareElement.createContainer()
    val spriteContainer = spriteElement.createContainer()

    var sprite: Sprite?
        get() = spriteElement.sprite
        set(value){ spriteElement.sprite = value }

    init{
        uiNode.addContainer(squareContainer){ uiNode.bounds }
        uiNode.addContainer(spriteContainer){ uiNode.bounds.fixRatioElement(spriteElement) }
    }
}
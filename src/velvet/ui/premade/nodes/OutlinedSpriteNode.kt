package velvet.ui.premade.nodes

import velvet.main.game.graphics.Sprite
import velvet.ui.UINode
import velvet.ui.vcontainer.velements.SpriteElement
import velvet.ui.vcontainer.velements.SquareElement

class OutlinedSpriteNode : UINode(){

    val squareElement = SquareElement()
    val spriteElement = SpriteElement()

    var sprite: Sprite?
        get() = spriteElement.sprite
        set(value) { spriteElement.sprite = value }

    val squareContainer = squareElement.createContainer()
    val spriteContainer = spriteElement.createContainer()

    init{
        add(squareContainer){ bounds }
        add(spriteContainer){ bounds.fixRatioElement(spriteElement) }
    }
}
package velvet.ui.premade.nodes

import velvet.main.game.graphics.Sprite
import velvet.ui.UINode
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.premade.components.ASyncSpriteLoader
import velvet.ui.vcontainer.velements.SpriteElement
import velvet.ui.vcontainer.velements.SquareElement

class OutlinedSpriteNode : UINode(){

    val squareElement = SquareElement()
    val spriteElement = SpriteElement()
    val aSyncLoader = ASyncSpriteLoader(spriteElement)

    var sprite: Sprite?
        get() = spriteElement.sprite
        set(value) { spriteElement.sprite = value }

    init{
        add(squareElement)
        add(basic(spriteElement), Layout.new().fixRatio(spriteElement))
    }
}
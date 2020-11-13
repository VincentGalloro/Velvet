package velvet.ui.premade

import velvet.game.graphics.Sprite
import velvet.ui.UINode
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.components.functional.ASyncSpriteLoader
import velvet.ui.velements.SpriteElement
import velvet.ui.velements.SquareElement

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
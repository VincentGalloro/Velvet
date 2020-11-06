package velvet.ui.velements

import velvet.main.VGraphics
import velvet.game.graphics.Sprite
import velvet.util.types.spatial.Area

class SpriteElement (var sprite: Sprite? = null) : VElement {

    override val area: Area?
        get() = sprite?.size?.area

    override fun render(g: VGraphics, targetArea: Area) {
        sprite?.let {
            g.save()
            g.scale(targetArea.vector / it.size.vector)
            g.drawSprite(it)
            g.reset()
        }
    }
}
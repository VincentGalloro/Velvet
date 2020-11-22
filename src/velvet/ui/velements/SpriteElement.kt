package velvet.ui.velements

import velvet.main.VGraphics
import velvet.game.graphics.Sprite
import velvet.util.types.spatial.Area

class SpriteElement (var sprite: Sprite? = null) : VElement {

    override val size: Area?
        get() = sprite?.size?.toArea()

    override fun render(g: VGraphics, targetSize: Area) {
        sprite?.let {
            g.save()
            g.scale(targetSize.vector / it.size.vector)
            g.draw(it)
            g.reset()
        }
    }
}
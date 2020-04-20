package velvet.ui.vcontainer.velements

import velvet.main.VGraphics
import velvet.main.game.graphics.Sprite
import velvet.structs.Vector

class SpriteElement (var sprite: Sprite? = null) : VElement {

    override val size: Vector?
        get() = sprite?.size?.toVector()

    override fun render(g: VGraphics, targetSize: Vector) {
        sprite?.let {
            g.save()
            g.scale(targetSize / it.size.toVector())
            g.drawSprite(it)
            g.reset()
        }
    }
}
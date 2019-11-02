package velvet.velements.impl

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.velements.VElement
import java.awt.image.BufferedImage

class ImageElement (var image: BufferedImage? = null) : VElement {

    override val size: Vector
        get() = image?.let { Vector(it.width.toDouble(), it.height.toDouble()) } ?: Vector.ZERO

    override fun render(g: VGraphics) {
        image?.let { g.drawImage(it) }
    }
}
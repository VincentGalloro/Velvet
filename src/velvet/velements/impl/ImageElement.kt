package velvet.velements.impl

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.velements.VElement
import java.awt.image.BufferedImage

class ImageElement (var image: BufferedImage? = null) : VElement {

    override val size: Vector?
        get() = image?.let(::calculateImageSize)

    fun calculateImageSize(image: BufferedImage): Vector{
        return Vector(image.width.toDouble(), image.height.toDouble())
    }

    override fun render(g: VGraphics, targetSize: Vector) {
        image?.let {
            g.save()
            g.scale(targetSize.divide(calculateImageSize(it)))
            g.drawImage(it)
            g.reset()
        }
    }
}
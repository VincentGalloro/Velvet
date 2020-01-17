package velvet.main.game.graphics

import velvet.main.VGraphics
import velvet.structs.Position
import velvet.structs.Vector
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Sprite(val image: BufferedImage){

    companion object{
        fun emptySprite(size: Position) = Sprite(BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB))
        fun loadSprite(file: File) = Sprite(ImageIO.read(file))
    }

    val size = Position(image.width, image.height)

    fun applyTransform(transform: ((Sprite, VGraphics)->Unit)): Sprite {
        val newSprite = emptySprite(size)
        val g = VGraphics(newSprite.image.createGraphics())
        transform(newSprite, g)
        return newSprite
    }

    fun horizontalFlip() = applyTransform { _, g ->
        g.translate(Vector(size.x, 0))
        g.scale(Vector(-1, 1))
        g.drawSprite(this)
    }
}
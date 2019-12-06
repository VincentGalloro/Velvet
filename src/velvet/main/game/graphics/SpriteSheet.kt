package velvet.main.game.graphics

import velvet.structs.Position
import java.awt.image.BufferedImage

class SpriteSheet (private val img: BufferedImage,
                   private val tileSize: Position,
                   private val spriteSeparation: Int = 1) {

    fun getImage(position: Position): BufferedImage{
        val imgPos = position*(tileSize+spriteSeparation)
        return img.getSubimage(imgPos.x, imgPos.y, tileSize.x, tileSize.y)
    }
}
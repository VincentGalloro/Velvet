package velvet.main.game.graphics

import velvet.structs.Position
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class SpriteSheet (val img: BufferedImage,
                   private val tileSize: Position,
                   private val spriteSeparation: Position) {

    companion object {

        fun loadSpriteSheet(file: File, tileSize: Position, spriteSeparation: Position = Position(1))
                = SpriteSheet(ImageIO.read(file), tileSize, spriteSeparation)
    }

    fun createSprite(position: Position): Sprite{
        val imgPos = position * (tileSize + spriteSeparation)
        return Sprite(img.getSubimage(imgPos.x, imgPos.y, tileSize.x, tileSize.y))
    }

    fun createTrack(position: Position, frames: Int, frameOffset: Position = Position(1,0))
            = Track(List(frames) { createSprite(position + frameOffset * it) })
}
package velvet.main.game.graphics

import velvet.util.types.spatial.Position
import velvet.util.types.spatial.Size
import java.awt.image.BufferedImage
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

class SpriteSheet (val img: BufferedImage,
                   private val tileSize: Size,
                   private val spriteSeparation: Position) {

    companion object {

        fun loadSpriteSheet(path: Path, tileSize: Size, spriteSeparation: Position = Position(1))
                = SpriteSheet(ImageIO.read(Files.newInputStream(path)), tileSize, spriteSeparation)
    }

    fun createSprite(position: Position): Sprite{
        val imgPos = position * (tileSize.position + spriteSeparation)
        return Sprite(img.getSubimage(imgPos.x, imgPos.y, tileSize.width, tileSize.height))
    }

    fun createTrack(position: Position, frames: Int, frameOffset: Position = Position(1, 0))
            = Track(List(frames) { createSprite(position + frameOffset * it) })
}
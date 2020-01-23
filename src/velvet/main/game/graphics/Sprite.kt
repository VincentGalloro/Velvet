package velvet.main.game.graphics

import velvet.main.VGraphics
import velvet.structs.Grid
import velvet.structs.Position
import velvet.structs.VColor
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import java.io.File
import javax.imageio.ImageIO

class Sprite(val image: BufferedImage){

    companion object{

        fun emptySprite(size: Position) = Sprite(BufferedImage(size.x, size.y, BufferedImage.TYPE_INT_ARGB))
        fun copySprite(sprite: Sprite) = emptySprite(sprite.size).also { it.createGraphics().drawSprite(sprite) }
        fun loadSprite(file: File) = copySprite(Sprite(ImageIO.read(file)))

        fun fromGrid(grid: Grid<VColor>) = emptySprite(grid.size).also { sprite ->
            val dataBuffer = (sprite.image.raster.dataBuffer as DataBufferInt).data
            grid.size.gridIterate { dataBuffer[it.toIndex(grid.size.x)] = grid[it].toInt() }
        }
    }

    val size = Position(image.width, image.height)

    private fun createGraphics() = VGraphics(image.createGraphics())

    fun toGrid(): Grid<VColor> {
        val dataBuffer = (image.raster.dataBuffer as DataBufferInt).data
        return Grid(size){ VColor.fromInt(dataBuffer[it.toIndex(size.x)]) }
    }

    fun saveToFile(file: File) = ImageIO.write(image, "png", file)
}
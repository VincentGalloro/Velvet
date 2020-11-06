package velvet.game.graphics

import velvet.main.VGraphics
import velvet.util.types.DenseGrid
import velvet.util.types.Grid
import velvet.util.types.VColor
import velvet.util.types.spatial.Size
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import javax.imageio.ImageIO

class Sprite(val image: BufferedImage){

    companion object{

        fun emptySprite(size: Size) = Sprite(BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB))
        fun copySprite(sprite: Sprite) = emptySprite(sprite.size).also { it.createGraphics().drawSprite(sprite) }
        fun loadSprite(path: Path) = copySprite(Sprite(ImageIO.read(Files.newInputStream(path))))

        fun fromDenseGrid(grid: Grid<VColor>,
                          backgroundColor: VColor) = emptySprite(grid.size).also { sprite ->
            val dataBuffer = (sprite.image.raster.dataBuffer as DataBufferInt).data
            grid.itemsIndexed().forEach { (pos, color) ->
                dataBuffer[grid.toIndex(pos)] = color?.toInt() ?: backgroundColor.toInt()
            }
        }
    }

    val size = Size(image.width, image.height)

    private fun createGraphics() = VGraphics(image.createGraphics())

    fun toGrid(): Grid<VColor> {
        val dataBuffer = (image.raster.dataBuffer as DataBufferInt).data
        return DenseGrid.ofSize(size){ VColor.fromInt(dataBuffer[size.toIndex(it)]) }
    }

    fun saveToFile(file: File) = ImageIO.write(image, "png", file)
}
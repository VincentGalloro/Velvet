package velvet.ui.velements

import velvet.game.graphics.Sprite
import velvet.main.VGraphics
import velvet.util.types.VColor
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Vector
import java.awt.Canvas
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Toolkit
import java.awt.image.BufferedImage

class TextElement(_text: String = "",
                  _color: VColor = VColor.BLACK) : VElement {

    override var size: Area = Area()

    private var cachedImage: Sprite? = null

    var font: Font = Font("Franklin Gothic Medium", Font.PLAIN, 24)
        set(value){
            field = value
            fontMetrics = Canvas().getFontMetrics(value)
            updateCachedImage()
        }
    var text = _text
        set(value){
            field = value
            updateCachedImage()
        }
    var color = _color
        set(value){
            field = value
            updateCachedImage()
        }
    var textLayout: TextLayout = SingleLineLayout()
        set(value){
            field = value
            updateCachedImage()
        }

    private var fontMetrics: FontMetrics = Canvas().getFontMetrics(font)

    fun getFontMetrics() = fontMetrics

    init {
        updateCachedImage()
    }

    fun setFontResolution(fontSize: Int) {
        font = Font("Franklin Gothic Medium", Font.PLAIN, fontSize)
    }

    private fun updateCachedImage() {
        size = textLayout.getArea(text, fontMetrics)

        cachedImage = Sprite.emptySprite(size.toSize()).also {
            val g = it.createGraphics()
            g.baseGraphics.setRenderingHints(
                    Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints") as Map<*,*>)

            g.color = color
            g.font = font

            textLayout.render(g, text, fontMetrics)
        }
    }

    override fun render(g: VGraphics, targetSize: Area) {
        cachedImage?.let {
            g.save()
            g.scale(targetSize.vector / size)
            g.draw(it)
            g.reset()
        }
    }
}
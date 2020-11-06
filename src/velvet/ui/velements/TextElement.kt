package velvet.ui.velements

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

    override var area: Area = Area.ZERO

    private var cachedImage: BufferedImage? = null

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
        area = textLayout.getArea(text, fontMetrics)

        if(area.width.toInt() <= 0 || area.height.toInt() <= 0) return

        cachedImage = BufferedImage(area.width.toInt(), area.height.toInt(), BufferedImage.TYPE_INT_ARGB).also {
            val g = VGraphics(it.createGraphics())
            g.baseGraphics.setRenderingHints(
                    Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints") as Map<*,*>)

            g.color = color
            g.font = font

            textLayout.render(g, text, fontMetrics)
        }
    }

    override fun render(g: VGraphics, targetArea: Area) {
        cachedImage?.let {
            g.save()
            g.scale(targetArea.vector / area.vector)
            g.drawImage(it)
            g.reset()
        }
    }
}
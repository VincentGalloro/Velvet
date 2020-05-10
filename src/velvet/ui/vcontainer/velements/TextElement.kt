package velvet.ui.vcontainer.velements

import velvet.main.VGraphics
import velvet.structs.VColor
import velvet.structs.Vector
import java.awt.Canvas
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Toolkit
import java.awt.image.BufferedImage

class TextElement(_text: String = "",
                  _color: VColor = VColor.BLACK) : VElement {

    override var size: Vector = Vector()

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
        size = textLayout.getSize(text, fontMetrics)

        if(size.floor().min <= 0){ cachedImage = null; return; }

        cachedImage = BufferedImage(size.x.toInt(), size.y.toInt(), BufferedImage.TYPE_INT_ARGB).let {
            val g = VGraphics(it.createGraphics())
            g.baseGraphics.setRenderingHints(Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints") as Map<*,*>)

            g.color = color
            g.font = font

            textLayout.render(g, text, fontMetrics)

            it
        }
    }

    override fun render(g: VGraphics, targetSize: Vector) {
        cachedImage?.let {
            g.save()
            g.scale(targetSize / size)
            g.drawImage(it)
            g.reset()
        }
    }
}
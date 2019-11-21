package velvet.velements.impl

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.velements.VElement
import java.awt.Canvas
import java.awt.Color
import java.awt.Font
import java.awt.FontMetrics
import java.awt.image.BufferedImage

abstract class BasicTextElement(_text: String = "",
                                _color: Color = Color.BLACK,
                                fontResolution: Int = 24) : VElement {

    abstract override val size: Vector

    private lateinit var font: Font
    protected lateinit var fontMetrics: FontMetrics

    private lateinit var cachedImage: BufferedImage

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

    init {
        setFontResolution(fontResolution)
    }

    fun setFontResolution(fontSize: Int) {
        font = Font("GoogleSansRegular", Font.PLAIN, fontSize)
        fontMetrics = Canvas().getFontMetrics(font)

        updateCachedImage()
    }

    private fun updateCachedImage() {
        cachedImage = BufferedImage(size.x.toInt(), size.y.toInt(), BufferedImage.TYPE_INT_ARGB)

        val g = VGraphics(cachedImage.createGraphics())

        g.setColor(color)
        g.setFont(font)

        textRender(g)
    }

    override fun render(g: VGraphics, targetSize: Vector) {
        g.save()
        g.scale(targetSize.divide(size))
        g.drawImage(cachedImage)
        g.reset()
    }

    abstract fun textRender(g: VGraphics)
}
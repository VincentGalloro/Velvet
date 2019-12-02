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

    private var cachedImage: BufferedImage? = null

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
        if(size.floor().min <= 0){ cachedImage = null; return; }

        cachedImage = BufferedImage(size.x.toInt(), size.y.toInt(), BufferedImage.TYPE_INT_ARGB).let {
            val g = VGraphics(it.createGraphics())

            g.setColor(color)
            g.setFont(font)

            textRender(g)

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

    abstract fun textRender(g: VGraphics)
}
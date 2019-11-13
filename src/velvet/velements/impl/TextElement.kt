package velvet.velements.impl

import velvet.main.VGraphics
import velvet.structs.Vector
import java.awt.Color
import java.awt.geom.AffineTransform

class TextElement(_text: String = "",
                  _color: Color = Color.BLACK) : BasicTextElement(_text, _color) {

    override val size: Vector
        get() = Vector(fontMetrics.stringWidth(text).toDouble(),
                (fontMetrics.ascent + fontMetrics.descent).toDouble())

    fun getCharTransform(charIndex: Int): AffineTransform {
        val at = AffineTransform()
        at.translate(fontMetrics.stringWidth(text.substring(0, charIndex)).toDouble(), fontMetrics.ascent.toDouble())
        return at
    }

    override fun textRender(g: VGraphics) {
        g.drawString(text, Vector(0.0, fontMetrics.ascent.toDouble()))
    }
}
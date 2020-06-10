package velvet.ui.vcontainer.velements

import velvet.main.VGraphics
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Vector
import java.awt.FontMetrics

interface TextLayout {

    fun getArea(text: String, fontMetrics: FontMetrics): Area
    fun getCharPos(text: String, fontMetrics: FontMetrics, index: Int): Vector

    fun render(g: VGraphics, text: String, fontMetrics: FontMetrics)
}

class SingleLineLayout : TextLayout{

    override fun getArea(text: String, fontMetrics: FontMetrics) =
            Area(fontMetrics.stringWidth(text).toDouble(),
                    (fontMetrics.ascent + fontMetrics.descent).toDouble())

    override fun getCharPos(text: String, fontMetrics: FontMetrics, index: Int) =
            Vector(fontMetrics.stringWidth(text.substring(0, index)).toDouble(),
                    (fontMetrics.ascent + fontMetrics.descent).toDouble())

    override fun render(g: VGraphics, text: String, fontMetrics: FontMetrics) {
        g.drawString(text, Vector(0.0, fontMetrics.ascent.toDouble()))
    }
}

class MultiLineLayout(var width: Double, var lineSep: Double) : TextLayout{

    fun calculateLineBreaks(text: String, fontMetrics: FontMetrics): Sequence<Int> = sequence{
        yield(0)
        var last = 0
        for(index in 1 until text.length){
            if(fontMetrics.stringWidth(text.substring(last, index+1)) > width){
                yield(index)
                last = index
            }
        }
        yield(text.length)
    }

    override fun getArea(text: String, fontMetrics: FontMetrics): Area {
        val lineCount = calculateLineBreaks(text, fontMetrics).toList().size - 1
        return Area(width, (fontMetrics.ascent + fontMetrics.descent) * lineCount + lineSep * (lineCount - 1))
    }

    override fun getCharPos(text: String, fontMetrics: FontMetrics, index: Int): Vector {
        val lineBreaks = calculateLineBreaks(text.substring(0, index), fontMetrics).toList()
        return Vector(fontMetrics.stringWidth(text.substring(lineBreaks[lineBreaks.size - 2], index)).toDouble(),
                fontMetrics.ascent.toDouble() + (fontMetrics.ascent + fontMetrics.descent + lineSep) * (lineBreaks.size - 2))
    }

    override fun render(g: VGraphics, text: String, fontMetrics: FontMetrics) {
        val lineBreaks = calculateLineBreaks(text, fontMetrics).toList()
        for(index in 0 until lineBreaks.size-1){
            g.drawString(text.substring(lineBreaks[index], lineBreaks[index+1]),
                    Vector(0.0, fontMetrics.ascent.toDouble() +
                            (fontMetrics.ascent + fontMetrics.descent + lineSep) * index))
        }
    }

}
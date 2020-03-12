package velvet.vcontainer.velement

import velvet.main.VGraphics
import velvet.structs.VColor
import velvet.structs.Vector
import java.awt.BasicStroke
import java.awt.Shape

abstract class BasicShapeElement (var fillColor: VColor? = null,
                                  var outlineColor: VColor? = VColor.BLACK,
                                  var outlineThickness: Double = 4.0) : VElement {

    override val size: Vector? = null

    abstract fun getShape(size: Vector): Shape

    override fun render(g: VGraphics, targetSize: Vector) {
        fillColor?.let {
            g.setColor(it)
            g.fill(getShape(targetSize))
        }
        outlineColor?.let {
            g.setColor(it)
            g.setStroke(BasicStroke(outlineThickness.toFloat()))
            g.draw(getShape(targetSize))
        }
    }
}

package velvet.ui.vcontainer.velements

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
            g.color = it
            g.fill(getShape(targetSize))
        }
        outlineColor?.let {
            g.color = it
            g.stroke = BasicStroke(outlineThickness.toFloat())
            g.draw(getShape(targetSize))
        }
    }
}

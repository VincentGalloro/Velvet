package velvet.ui.vcontainer.velements

import velvet.structs.VColor
import velvet.structs.Vector
import java.awt.Shape
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D

class SquareElement (fillColor: VColor? = null,
                     outlineColor: VColor? = VColor.BLACK,
                     outlineThickness: Double = 4.0,
                     var rounding: Double? = null) : BasicShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(size: Vector): Shape {
        return rounding?.let {
            RoundRectangle2D.Double(0.0, 0.0, size.x, size.y, it, it)
        } ?: Rectangle2D.Double(0.0, 0.0, size.x, size.y)
    }
}
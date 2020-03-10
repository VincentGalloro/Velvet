package velvet.vcontainer.velement

import velvet.structs.Vector
import java.awt.Color
import java.awt.Shape
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D

class SquareElement (fillColor: Color? = null,
                     outlineColor: Color? = Color.BLACK,
                     outlineThickness: Double = 4.0,
                     var rounding: Double? = null) : BasicShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(size: Vector): Shape {
        return rounding?.let {
            RoundRectangle2D.Double(0.0, 0.0, size.x, size.y, it, it)
        } ?: Rectangle2D.Double(0.0, 0.0, size.x, size.y)
    }
}
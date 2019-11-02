package velvet.velements.impl

import velvet.structs.Vector
import java.awt.Shape
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D

class SquareElement (var rounding: Double? = null) : BasicShapeElement() {

    override fun getShape(size: Vector): Shape {
        return rounding?.let {
            RoundRectangle2D.Double(0.0, 0.0, size.x, size.y, it, it)
        } ?: Rectangle2D.Double(0.0, 0.0, size.x, size.y)
    }
}
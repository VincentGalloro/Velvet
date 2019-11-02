package velvet.velements.impl

import velvet.structs.Vector
import java.awt.Shape
import java.awt.geom.Ellipse2D

class CircleElement : BasicShapeElement() {

    override fun getShape(size: Vector): Shape {
        return Ellipse2D.Double(0.0, 0.0, size.x, size.y)
    }
}

package velvet.vcontainer.velement

import velvet.structs.Vector
import java.awt.Color
import java.awt.Shape
import java.awt.geom.Ellipse2D

class CircleElement(fillColor: Color? = null,
                    outlineColor: Color? = Color.BLACK,
                    outlineThickness: Double = 4.0) : BasicShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(size: Vector): Shape {
        return Ellipse2D.Double(0.0, 0.0, size.x, size.y)
    }
}

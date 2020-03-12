package velvet.vcontainer.velement

import velvet.structs.VColor
import velvet.structs.Vector
import java.awt.Shape
import java.awt.geom.Ellipse2D

class CircleElement(fillColor: VColor? = null,
                    outlineColor: VColor? = VColor.BLACK,
                    outlineThickness: Double = 4.0) : BasicShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(size: Vector): Shape {
        return Ellipse2D.Double(0.0, 0.0, size.x, size.y)
    }
}

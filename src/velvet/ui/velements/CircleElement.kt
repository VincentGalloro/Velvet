package velvet.ui.velements

import velvet.util.types.VColor
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Vector
import java.awt.Shape
import java.awt.geom.Ellipse2D

class CircleElement(fillColor: VColor? = null,
                    outlineColor: VColor? = VColor.BLACK,
                    outlineThickness: Double = 4.0) : BasicShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(area: Area): Shape {
        return Ellipse2D.Double(0.0, 0.0, area.width, area.height)
    }
}

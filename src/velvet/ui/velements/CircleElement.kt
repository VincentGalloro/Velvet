package velvet.ui.velements

import velvet.util.types.VCircle
import velvet.util.types.VColor
import velvet.util.types.VShape
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Vector
import java.awt.Shape
import java.awt.geom.Ellipse2D

class CircleElement(fillColor: VColor? = null,
                    outlineColor: VColor? = VColor.BLACK,
                    outlineThickness: Double = 4.0) : BasicShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(area: Area) = VCircle(Vector(), area)
}

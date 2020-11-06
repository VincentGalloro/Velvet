package velvet.ui.velements

import velvet.util.types.VColor
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Vector
import java.awt.Shape
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D

class SquareElement (fillColor: VColor? = null,
                     outlineColor: VColor? = VColor.BLACK,
                     outlineThickness: Double = 4.0,
                     var rounding: Double? = null) : BasicShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(area: Area): Shape {
        return rounding?.let {
            RoundRectangle2D.Double(0.0, 0.0, area.width, area.height, it, it)
        } ?: Rectangle2D.Double(0.0, 0.0, area.width, area.height)
    }
}
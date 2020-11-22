package velvet.ui.velements

import velvet.util.types.VCircle
import velvet.util.types.VColor
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Vector

class CircleElement(fillColor: VColor? = null,
                    outlineColor: VColor? = VColor.BLACK,
                    outlineThickness: Double = 4.0) : ShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(area: Area) = VCircle(Vector(), area)
}

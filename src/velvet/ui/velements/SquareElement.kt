package velvet.ui.velements

import velvet.util.types.VColor
import velvet.util.types.VRect
import velvet.util.types.VRoundedRect
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Vector

class SquareElement (fillColor: VColor? = null,
                     outlineColor: VColor? = VColor.BLACK,
                     outlineThickness: Double = 4.0,
                     var rounding: Double? = null) : ShapeElement(fillColor, outlineColor, outlineThickness) {

    override fun getShape(area: Area) = rounding?.let {
        VRoundedRect(Vector(), area, it)
    } ?: VRect(Vector(), area)
}
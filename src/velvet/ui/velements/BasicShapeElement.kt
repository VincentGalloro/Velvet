package velvet.ui.velements

import velvet.main.VGraphics
import velvet.util.types.VColor
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Vector
import java.awt.BasicStroke
import java.awt.Shape

abstract class BasicShapeElement (var fillColor: VColor? = null,
                                  var outlineColor: VColor? = VColor.BLACK,
                                  var outlineThickness: Double = 4.0) : VElement {

    override val area: Area? = null

    abstract fun getShape(area: Area): Shape

    override fun render(g: VGraphics, targetArea: Area) {
        fillColor?.let {
            g.color = it
            g.fill(getShape(targetArea))
        }
        outlineColor?.let {
            g.color = it
            g.stroke = BasicStroke(outlineThickness.toFloat())
            g.draw(getShape(targetArea))
        }
    }
}

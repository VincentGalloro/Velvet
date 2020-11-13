package velvet.ui.velements

import velvet.main.VGraphics
import velvet.util.types.VColor
import velvet.util.types.VShape
import velvet.util.types.spatial.Area
import java.awt.BasicStroke

abstract class BasicShapeElement (var fillColor: VColor? = null,
                                  var outlineColor: VColor? = VColor.BLACK,
                                  var outlineThickness: Double = 4.0) : VElement {

    override val size: Area? = null

    abstract fun getShape(area: Area): VShape

    override fun render(g: VGraphics, targetSize: Area) {
        fillColor?.let {
            g.color = it
            g.fill(getShape(targetSize))
        }
        outlineColor?.let {
            g.color = it
            g.stroke = BasicStroke(outlineThickness.toFloat())
            g.draw(getShape(targetSize))
        }
    }
}

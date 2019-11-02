package velvet.velements.impl

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.velements.VDecorator
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Shape

abstract class BasicShapeElement (var fillColor: Color? = null,
                                  var outlineColor: Color? = Color.BLACK,
                                  var outlineThickness: Double = 4.0) : VDecorator {

    abstract fun getShape(size: Vector): Shape

    override fun render(g: VGraphics, size: Vector) {
        fillColor?.let {
            g.setColor(it)
            g.fill(getShape(size))
        }
        outlineColor?.let {
            g.setColor(it)
            g.setStroke(BasicStroke(outlineThickness.toFloat()))
            g.draw(getShape(size))
        }
    }
}

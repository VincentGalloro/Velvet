package velvet.velements.container

import velvet.main.VGraphics
import velvet.velements.VElement
import java.awt.geom.AffineTransform

open class VContainer(var vElement: VElement? = null) {

    var containerLayout: ContainerLayout = ContainerLayout()
    var parentContainer: VContainer? = null

    val transform: AffineTransform
        get(){
            val at = parentContainer?.transform ?: AffineTransform()
            at.translate(containerLayout.pos.x, containerLayout.pos.y)
            at.rotate(containerLayout.angle)
            return at
        }

    fun render(g: VGraphics){
        vElement?.let {
            g.save()

            g.transform(transform)
            g.translate(containerLayout.origin.multiply(containerLayout.size).negate())
            it.render(g, containerLayout.size)

            g.reset()
        }
    }
}
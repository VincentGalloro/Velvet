package velvet.velements.container

import velvet.main.VGraphics
import velvet.velements.VElement

open class VContainer(var vElement: VElement? = null) {

    var containerLayout: ContainerLayout = ContainerLayout()

    fun render(g: VGraphics){
        vElement?.let {
            g.save()

            g.translate(containerLayout.pos)
            g.rotate(containerLayout.angle)
            g.translate(containerLayout.size.half().negate())
            it.render(g, containerLayout.size)

            g.reset()
        }
    }
}
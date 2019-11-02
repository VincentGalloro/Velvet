package velvet.velements.container

import velvet.main.VGraphics
import velvet.velements.VDecorator

class VElementContainer(var element: VDecorator? = null) {

    var containerLayout: ContainerLayout = ContainerLayout()

    fun render(g: VGraphics) {
        element?.let { e ->
            g.save()

            g.translate(containerLayout.pos)
            g.rotate(containerLayout.angle)
            g.translate(containerLayout.origin.negate().multiply(containerLayout.size))

            e.render(g, containerLayout.size)
            g.reset()
        }
    }
}
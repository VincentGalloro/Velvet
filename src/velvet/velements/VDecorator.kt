package velvet.velements

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.velements.container.ContainerLayout
import velvet.velements.container.ContainerLayoutRenderStrategy

interface VDecorator : ContainerLayoutRenderStrategy {

    fun render(g: VGraphics, size: Vector)

    override fun render(g: VGraphics, containerLayout: ContainerLayout){
        g.save()

        g.translate(containerLayout.pos)
        g.rotate(containerLayout.angle)
        g.translate(containerLayout.origin.negate().multiply(containerLayout.size))

        render(g, containerLayout.size)

        g.reset()
    }
}
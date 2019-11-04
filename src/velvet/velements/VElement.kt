package velvet.velements

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.velements.container.ContainerLayout
import velvet.velements.container.ContainerLayoutRenderStrategy

interface VElement : ContainerLayoutRenderStrategy {

    val size: Vector

    fun render(g: VGraphics)

    override fun render(g: VGraphics, containerLayout: ContainerLayout){
        g.save()

        g.translate(containerLayout.pos)
        g.rotate(containerLayout.angle)
        g.scale(containerLayout.size)
        g.translate(containerLayout.origin.negate())
        g.scale(size.invert())

        render(g)

        g.reset()
    }
}

package velvet.velements.container

import velvet.main.VGraphics

interface ContainerLayoutRenderStrategy {

    fun render(g: VGraphics, containerLayout: ContainerLayout)
}
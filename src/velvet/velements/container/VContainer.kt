package velvet.velements.container

import velvet.main.VGraphics

class VContainer(var vUnit: ContainerLayoutRenderStrategy? = null) {

    var containerLayout: ContainerLayout = ContainerLayout()

    fun render(g: VGraphics) = vUnit?.render(g, containerLayout)
}
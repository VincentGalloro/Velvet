package velvet.ui

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.structs.Vector
import velvet.ui.vcontainer.VContainer
import velvet.ui.premade.components.UIComponent
import velvet.ui.vcontainer.velements.VElement

class UINode{

    var bounds = Bounds()
    var boundsGenerator: (() -> Bounds)? = null

    val subNodes: MutableList<UINode> = mutableListOf()
    val vContainers: MutableList<Pair<VContainer, (() -> Bounds)>> = mutableListOf()
    val uiComponents: MutableList<UIComponent> = mutableListOf()

    var enabled = true

    fun isHovered(pos: Vector) = bounds.contains(pos) && enabled

    fun addElement(vElement: VElement, boundsGenerator: ()->Bounds)
            = vContainers.add(VContainer(vElement) to boundsGenerator)
    fun addContainer(vContainer: VContainer, boundsGenerator: () -> Bounds)
            = vContainers.add(vContainer to boundsGenerator)

    fun update() {
        if(!enabled) return

        boundsGenerator?.invoke()?.also { bounds = it }

        uiComponents.forEach { it.preUpdate(this) }
        vContainers.forEach { (vContainer, boundsGenerator) -> vContainer.bounds = boundsGenerator() }
        subNodes.forEach { it.update() }
        uiComponents.asReversed().forEach { it.postUpdate(this) }
    }

    fun render(g: VGraphics) {
        if(!enabled) return

        uiComponents.forEach { it.preRender(this, g) }
        vContainers.forEach { (vContainer, _) -> vContainer.render(g) }
        subNodes.asReversed().forEach { it.render(g) }
        uiComponents.asReversed().forEach { it.postRender(this, g) }
    }
}
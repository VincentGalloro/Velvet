package velvet.ui

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.structs.Vector
import velvet.ui.vcontainer.VContainer
import velvet.ui.premade.components.UIComponent
import velvet.ui.vcontainer.velements.VElement

open class UINode {

    var bounds = Bounds()
    var boundsGenerator: (() -> Bounds)? = null
    val boundsProcessors: MutableList<(Bounds)->Bounds> = mutableListOf()

    val subNodes: MutableList<UINode> = mutableListOf()
    val vContainers: MutableList<Pair<VContainer, (() -> Bounds)>> = mutableListOf()
    val uiComponents: MutableList<UIComponent> = mutableListOf()
    val activeComponents: Sequence<UIComponent> get() = uiComponents.asSequence().filter { it.enabled }

    var enabled = true

    fun isHovered(pos: Vector) = bounds.contains(pos) && enabled

    fun add(vElement: VElement, boundsGenerator: ()->Bounds)
            = vContainers.add(VContainer(vElement) to boundsGenerator)
    fun add(vContainer: VContainer, boundsGenerator: () -> Bounds)
            = vContainers.add(vContainer to boundsGenerator)
    fun add(uiComponent: UIComponent)
            = uiComponents.add(uiComponent)
    fun add(uiNode: UINode, boundsGenerator: () -> Bounds)
            = subNodes.add(uiNode.also { it.boundsGenerator = boundsGenerator })

    fun update() {
        if(!enabled) return

        boundsGenerator?.invoke()?.let {
            bounds = boundsProcessors.fold(it){ acc, f -> f(acc) }
        }

        activeComponents.forEach { it.preUpdate(this) }
        vContainers.forEach { (vContainer, boundsGenerator) -> vContainer.bounds = boundsGenerator() }
        subNodes.forEach { it.update() }
        activeComponents.toList().asReversed().forEach { it.postUpdate(this) }
    }

    fun render(g: VGraphics) {
        if(!enabled) return

        activeComponents.forEach { it.preRender(this, g) }
        vContainers.forEach { (vContainer, _) -> vContainer.render(g) }
        subNodes.forEach { it.render(g) }
        activeComponents.toList().asReversed().forEach { it.postRender(this, g) }
    }
}
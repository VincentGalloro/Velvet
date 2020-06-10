package velvet.ui

import velvet.main.VGraphics
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Vector
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.premade.components.UIComponent
import velvet.ui.vcontainer.velements.VElement

open class UINode {

    companion object{
        fun basic(vElement: VElement) = UINode().also { it.add(vElement) }
    }

    var bounds = Bounds()
    var layout: Layout = Layout.empty()

    val subNodes: MutableList<UINode> = mutableListOf()
    val vElements: MutableList<VElement> = mutableListOf()
    val uiComponents: MutableList<UIComponent> = mutableListOf()

    val activeComponents: Sequence<UIComponent> get() = uiComponents.asSequence().filter { it.enabled }

    var enabled = true

    fun isHovered(pos: Vector) = bounds.contains(pos) && enabled
    fun findHoveredSubNode(pos: Vector) = subNodes.asReversed().find { it.isHovered(pos) }

    fun add(uiComponent: UIComponent) = uiComponents.add(uiComponent)
    fun add(vElement: VElement) = vElements.add(vElement)
    fun add(uiNode: UINode, layout: Layout = Layout.empty()) = subNodes.add(uiNode.also { it.layout = layout })

    fun remove(uiNode: UINode) = subNodes.remove(uiNode)

    fun clearVElements() = vElements.clear()
    fun clearSubNodes() = subNodes.clear()

    fun update() {
        if(!enabled) return

        activeComponents.forEach { it.preUpdate(this) }
        subNodes.forEachIndexed { index, subNode ->
            subNode.bounds = subNode.layout(bounds, index)
            subNode.update()
        }
        activeComponents.toList().asReversed().forEach { it.postUpdate(this) }
    }

    fun render(g: VGraphics) {
        if(!enabled) return

        activeComponents.forEach { it.preRender(this, g) }
        vElements.forEach { it.render(g, bounds) }
        subNodes.forEach { it.render(g) }
        activeComponents.toList().asReversed().forEach { it.postRender(this, g) }
    }
}
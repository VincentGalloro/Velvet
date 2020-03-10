package velvet.vcontainer.interact

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.structs.Vector

class UINodeImpl : UINode{

    override var bounds = Bounds()
    override var boundsGenerator: (() -> Bounds)? = null

    override val subNodes: MutableList<UINode> = mutableListOf()
    override val containers: MutableList<TrackedVContainer> = mutableListOf()

    override val uiEventListener = UIEventListener()
    override val enabled = true

    override fun isHovered(pos: Vector) = bounds.contains(pos) && enabled

    override fun update() {
        if(!enabled) return

        boundsGenerator?.invoke()?.also { bounds = it }

        containers.forEach { it.update() }
        subNodes.forEach { it.update() }
    }

    override fun render(g: VGraphics) {
        if(!enabled) return

        containers.forEach { it.render(g) }
        subNodes.asReversed().forEach { it.render(g) }
    }
}
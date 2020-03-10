package velvet.vcontainer.interact

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.structs.Vector

interface UINode {

    var bounds: Bounds
    var boundsGenerator: (()->Bounds)?

    val subNodes: MutableList<UINode>
    val containers: MutableList<TrackedVContainer>

    val uiEventListener: UIEventListener
    val enabled: Boolean

    fun isHovered(pos: Vector): Boolean

    fun update()
    fun render(g: VGraphics)
}
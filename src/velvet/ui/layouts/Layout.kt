package velvet.ui.layouts

import velvet.structs.Bounds
import velvet.ui.UINode

class Layout(private val boundsModifiers: List<(Bounds)->Bounds>) {

    companion object{
        fun new() = Layout(emptyList())
    }

    fun createBoundsGenerator(uiNode: UINode): ()->Bounds = { boundsModifiers.fold(uiNode.bounds){ b, f -> f(b) } }

    fun addModifier(boundsModifier: (Bounds) -> Bounds) = Layout(boundsModifiers + boundsModifier)

    fun horizontalSplit() = HorizontalSplitBuilder(this)
    fun verticalSplit() = VerticalSplitBuilder(this)
}
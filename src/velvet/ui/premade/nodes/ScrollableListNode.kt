package velvet.ui.premade.nodes

import velvet.structs.Vector
import velvet.ui.UINode
import velvet.ui.premade.components.ScrollComponent
import velvet.ui.premade.components.WindowedComponent
import velvet.ui.premade.layouts.Layout

class ScrollableListNode(layout: Layout){

    val uiNode = UINode()

    var padding = Vector()
    val scrollComponent = ScrollComponent(layout)

    init{
        uiNode.uiComponents.add(WindowedComponent())
        uiNode.uiComponents.add(scrollComponent)
    }

    fun loadNodes(nodes: List<UINode>){
        uiNode.subNodes.clear()
        uiNode.subNodes.addAll(nodes)
        scrollComponent.visibleNodes = emptySet()

        uiNode.subNodes.forEachIndexed { index, uiNode ->
            uiNode.enabled = false
            uiNode.boundsGenerator = {
                scrollComponent.layout.getBounds(index,
                        uiNode.bounds.resize(-padding*2, Vector(0.5)),
                        scrollComponent.scroll)
            }
        }
    }
}
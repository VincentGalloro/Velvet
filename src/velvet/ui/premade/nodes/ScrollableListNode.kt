package velvet.ui.premade.nodes

import velvet.ui.UINode
import velvet.ui.layouts.LLayout
import velvet.ui.premade.components.ScrollComponent
import velvet.ui.premade.components.WindowedComponent
import velvet.ui.layouts.Layout

class ScrollableListNode(layout: LLayout) : UINode(){

    val scrollComponent = ScrollComponent(layout)

    init{
        add(WindowedComponent())
        add(scrollComponent)
    }

    fun loadNodes(nodes: List<UINode>){
        subNodes.clear()
        scrollComponent.visibleNodes = emptySet()
        subNodes.forEachIndexed { index, childNode ->
            childNode.enabled = true //TODO: change back to false once visibleNodes is fixed
            add(childNode){
                scrollComponent.layout.getBounds(bounds, index, nodes.size, scrollComponent.scroll)
            }
        }
    }
}
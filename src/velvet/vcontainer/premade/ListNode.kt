package velvet.vcontainer.premade

import velvet.vcontainer.interact.UINode
import velvet.vcontainer.interact.UINodeImpl
import velvet.vcontainer.premade.layouts.Layout

class ListNode(var layout: Layout) : UINode by UINodeImpl() {

    fun loadNodes(nodes: List<UINode>){
        subNodes.clear()
        subNodes.addAll(nodes)

        subNodes.forEachIndexed { index, uiNode ->
            uiNode.boundsGenerator = { layout.getBounds(index, bounds) }
        }
    }
}
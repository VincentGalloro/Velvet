package velvet.ui.premade.nodes

import velvet.structs.Vector
import velvet.ui.UINode
import velvet.ui.premade.layouts.Layout

class ListNode(var layout: Layout) {

    val uiNode = UINode()

    var padding = Vector()

    fun loadNodes(nodes: List<UINode>){
        uiNode.subNodes.clear()
        uiNode.subNodes.addAll(nodes)

        uiNode.subNodes.forEachIndexed { index, uiNode ->
            uiNode.boundsGenerator = { layout.getBounds(index, uiNode.bounds.resize(-padding*2, Vector(0.5))) }
        }
    }
}
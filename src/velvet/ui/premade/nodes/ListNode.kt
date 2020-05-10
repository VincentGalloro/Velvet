package velvet.ui.premade.nodes

import velvet.ui.UINode
import velvet.ui.layouts.LLayout
import velvet.ui.layouts.Layout

class ListNode(var layout: LLayout) : UINode() {

    fun loadNodes(nodes: List<UINode>){
        subNodes.clear()
        nodes.forEachIndexed { index, childNode ->
            add(childNode) { layout.getBounds(bounds, index, nodes.size) }
        }
    }
}
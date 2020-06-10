package velvet.ui.premade.nodes

import velvet.ui.UINode
import velvet.ui.boundsprocessors.layouts.Layout

class ListNode : UINode() {

    fun loadNodes(nodes: List<UINode>, subNodeLayout: Layout){
        clearSubNodes()
        nodes.forEach { childNode -> add(childNode, subNodeLayout) }
    }
}
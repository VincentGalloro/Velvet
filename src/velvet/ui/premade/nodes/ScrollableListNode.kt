package velvet.ui.premade.nodes

import velvet.ui.UINode
import velvet.ui.boundsprocessors.layouts.CompiledListLayout
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.boundsprocessors.layouts.ListLayout
import velvet.ui.premade.components.ScrollComponent
import velvet.ui.premade.components.WindowedComponent

class ScrollableListNode<T : UINode> : UINode(){

    private var _items: List<T> = emptyList()
    val items get() = _items

    private val scrollComponent = ScrollComponent()

    var onItemEnter: ((T)->Unit)? = null
    var onItemExit: ((T)->Unit)? = null

    init{
        add(WindowedComponent())
        add(scrollComponent)

        scrollComponent.onItemEnter = { index ->
            subNodes.getOrNull(index)?.let { subNode -> subNode.enabled = true }
            _items.getOrNull(index)?.let { item -> onItemEnter?.invoke(item) }
        }
        scrollComponent.onItemExit = { index ->
            subNodes.getOrNull(index)?.let { subNode -> subNode.enabled = false }
            _items.getOrNull(index)?.let { item -> onItemExit?.invoke(item) }
        }
    }

    fun loadNodes(items: List<T>, subNodeLayout: Layout){
        scrollComponent.visibleNodes = emptySet() //reset scroll visible (all sub nodes will disable)
        subNodes.forEach { it.enabled = true } //re-enable all sub-nodes in-case re-used
        clearSubNodes()
        items.forEach {
            it.enabled = false
            add(it, subNodeLayout)
        }
        _items = items
    }

    fun loadNodes(items: List<T>, compiledListLayout: CompiledListLayout)
            = loadNodes(items, compiledListLayout.bind(scrollComponent))
}
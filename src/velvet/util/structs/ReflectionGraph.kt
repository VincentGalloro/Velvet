package velvet.util.structs

class ReflectionGraph<N, L> private constructor(
    private val graph: MutableGraph<N, L>,
    private val itemsGetter: ()->Collection<N>,
    private val itemLinksGetter: (N)->Collection<Pair<N, L>>
) : Graph<N, L> by graph{

    companion object{

        operator fun <N, L> invoke(itemsGetter: ()->Collection<N>,
                                   itemLinksGetter: (N)->Collection<Pair<N, L>>)
                = ReflectionGraph(BasicGraph(), itemsGetter, itemLinksGetter)
    }

    //add/remove nodes to match items set
    private fun updateItems(){
        val items = itemsGetter().toSet()
        val current = graph.nodes

        (items - current).forEach { graph.add(it) }
        (current - items).forEach { graph.remove(it) }
    }

    //update outgoing links from item
    private fun updateConnections(item: N){
        val connections = itemLinksGetter(item)
        val connectionNodes = connections.map { it.first }.toSet()
        val current = graph.nodesFrom(item)

        connections.forEach { graph.link(item, it.first, it.second) }
        (current - connectionNodes).forEach { graph.unlink(item, it) }
    }

    fun update(item: N){
        val items = itemsGetter().toSet()
        if(item !in items){
            graph.remove(item)
            return
        }

        val connectionNodes = itemLinksGetter(item).map { it.first }
        connectionNodes.filter { it in items }.forEach { graph.add(it) }

        updateConnections(item)
    }

    //update full graph to match items state
    fun update(){
        updateItems()
        graph.nodes.toSet().forEach { updateConnections(it) }
    }
}
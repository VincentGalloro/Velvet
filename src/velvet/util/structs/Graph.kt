package velvet.util.structs

interface Graph<N, L> {

    val nodes: Set<N>
    val links: Map<Pair<N, N>, L>

    operator fun contains(node: N): Boolean
    fun isLinked(start: N, end: N): Boolean
    fun linkBetween(start: N, end: N): L?

    fun nodesFrom(node: N): Set<N>
    fun nodesTo(node: N): Set<N>

    override fun toString(): String
}

interface MutableGraph<N, L> : Graph<N, L>{

    fun add(node: N)
    fun link(start: N, end: N, link: L)

    fun remove(node: N)
    fun unlink(start: N, end: N)
}

class BasicGraph<N, L>: MutableGraph<N, L>{

    override val nodes: MutableSet<N> = mutableSetOf()
    private val connections: MutableMap<N, MutableSet<N>> = mutableMapOf()
    private val reversedConnections: MutableMap<N, MutableSet<N>> = mutableMapOf()
    override val links: MutableMap<Pair<N, N>, L> = mutableMapOf()

    override fun contains(node: N) = node in nodes
    override fun isLinked(start: N, end: N) = start to end in links
    override fun linkBetween(start: N, end: N): L? = links[start to end]

    override fun nodesFrom(node: N) = connections[node]?.toSet() ?: emptySet()
    override fun nodesTo(node: N) = reversedConnections[node]?.toSet() ?: emptySet()

    override fun add(node: N) {
        nodes.add(node)
    }
    override fun link(start: N, end: N, link: L) {
        if(start !in nodes || end !in nodes) return
        connections.getOrPut(start){ mutableSetOf() }.add(end)
        reversedConnections.getOrPut(end){ mutableSetOf() }.add(start)
        links[start to end] = link
    }

    override fun remove(node: N) {
        nodes.remove(node)
        nodesFrom(node).forEach { unlink(node, it) }
        nodesTo(node).forEach { unlink(it, node) }
    }
    override fun unlink(start: N, end: N) {
        connections[start]?.remove(end)
        reversedConnections[end]?.remove(start)
        links.remove(start to end)

        if(connections[start]?.isEmpty() == true) connections.remove(start)
        if(reversedConnections[end]?.isEmpty() == true) reversedConnections.remove(end)
    }

    override fun toString(): String {
        return "nodes: ${nodes}\n${nodes.joinToString("\n") { 
            "$it: ${nodesFrom(it).map { n -> "$n${linkBetween(it, n)?.let { l -> " ($l)" }}" }}" 
        }}"
    }
}
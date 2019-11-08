package velvet.multithreading

class SynchronizedLRUCache<K,V>(private val maxItems: Int) {

    private var firstNode: KeyValueNode<K,V>? = null
    private var lastNode: KeyValueNode<K,V>? = null
    private val nodes: HashMap<K, KeyValueNode<K,V>> = HashMap()

    @Synchronized
    private fun removeNode(node: KeyValueNode<K,V>){
        if(node == firstNode){ firstNode = node.nextNode }
        if(node == lastNode){ lastNode = node.prevNode }

        val prev = node.prevNode
        val next = node.nextNode

        prev?.nextNode = next
        next?.prevNode = prev
    }

    @Synchronized
    private fun moveToFront(node: KeyValueNode<K,V>) {
        removeNode(node)
        node.prevNode = null
        addToFront(node)
    }

    @Synchronized
    private fun addToFront(node: KeyValueNode<K, V>){
        node.nextNode = firstNode

        firstNode?.prevNode = node
        firstNode = node
    }

    @Synchronized
    fun add(key: K, value: V){
        val node = KeyValueNode(key, value, nextNode = firstNode)
        addToFront(node)

        if(lastNode==null){ lastNode = node }
        nodes[key] = node

        if(nodes.size > maxItems){
            lastNode?.let { removeNode(it) }
        }
    }

    @Synchronized
    fun get(key: K): V?{
        val node = nodes[key]
        node?.let { moveToFront(it) }

        return node?.value
    }

    @Synchronized
    fun forEach(consumer: (K,V)->Unit){
        var node = firstNode
        while(node != null){
            consumer.invoke(node.key, node.value)
            node = node.nextNode
        }
    }

    @Synchronized
    fun forEachReverse(consumer: (K,V)->Unit){
        var node = lastNode
        while(node != null){
            consumer.invoke(node.key, node.value)
            node = node.prevNode
        }
    }
}
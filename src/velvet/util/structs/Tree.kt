package velvet.util.structs

interface Tree<T : Any> {

    var root: T

    fun getParent(item: T): T?
    fun getParentChain(item: T): List<T>
    fun getChildren(item: T): List<T>

    fun addItem(item: T, parent: T): Boolean
    fun substituteItem(oldItem: T, newItem: T): Boolean
    fun deleteItem(item: T): Boolean
}

class TreeImpl<T : Any>(_root: T) : Tree<T>{

    private val items: MutableSet<T> = mutableSetOf()
    private val parentMap: MutableMap<T, T> = mutableMapOf()
    private val childMap: MutableMap<T, MutableSet<T>> = mutableMapOf()

    override var root: T = _root
        set(value) {
            if(substituteItem(field, value)) {
                field = value
            }
        }

    override fun getParent(item: T) = parentMap[item]
    override fun getParentChain(item: T) = generateSequence(item, ::getParent).toList()
    override fun getChildren(item: T) = childMap.getOrElse(item, ::mutableSetOf).toList()

    override fun addItem(item: T, parent: T): Boolean{
        if(item in items || parent !in items) return false

        items.add(item)
        parentMap[item] = parent
        childMap.getOrPut(parent, ::mutableSetOf).add(item)

        return true
    }

    override fun substituteItem(oldItem: T, newItem: T): Boolean{
        if(oldItem==newItem || oldItem !in items || newItem in items) return false

        items.remove(oldItem)
        items.add(newItem)

        //fix parental relation
        parentMap[oldItem]?.let { parent ->
            parentMap[newItem] = parent

            childMap[parent]?.remove(oldItem)
            childMap[parent]?.add(newItem)
        }
        parentMap.remove(oldItem)

        //fix children
        childMap[oldItem]?.let { children ->
            childMap[newItem] = children

            children.forEach { parentMap[it] = newItem }
        }
        childMap.remove(oldItem)

        return true
    }

    override fun deleteItem(item: T): Boolean {
        if (item !in items) return false

        //recursive delete subtree
        getChildren(item).forEach { deleteItem(it) }

        items.remove(item)
        parentMap.remove(item)?.let { parent ->
            childMap[parent]?.remove(item)
        }

        return true
    }
}
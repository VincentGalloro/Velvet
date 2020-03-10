package velvet.structs

interface Hierarchy<T : Any>{

    fun getPairs(): List<Pair<T, T>>
    fun getParents(item: T): List<T>
    fun getChildren(item: T): List<T>

    fun addRelation(child: T, parent: T)
    fun removeRelation(child: T, parent: T)

    fun deleteItem(item: T)
}

class HierarchyImpl<T : Any> : Hierarchy<T> {

    private val pairs: MutableSet<Pair<T, T>> = mutableSetOf()
    private val parentMap: MutableMap<T, MutableSet<T>> = mutableMapOf()
    private val childMap: MutableMap<T, MutableSet<T>> = mutableMapOf()

    override fun getPairs() = pairs.toList()
    override fun getParents(item: T) = (parentMap[item] ?: mutableSetOf()).toList()
    override fun getChildren(item: T) = (childMap[item] ?: mutableSetOf()).toList()

    override fun addRelation(child: T, parent: T){
        if(child == parent) return
        if((child to parent) in pairs) return

        pairs.add(child to parent)

        parentMap.getOrPut(child, ::mutableSetOf).add(parent)
        childMap.getOrPut(parent, ::mutableSetOf).add(child)
    }

    override fun removeRelation(child: T, parent: T){
        parentMap[child]?.remove(parent)
        childMap[parent]?.remove(child)
        pairs.remove(child to parent)

        if(parentMap[child].isNullOrEmpty() && childMap[child].isNullOrEmpty()){ deleteItem(child) }
        if(parentMap[parent].isNullOrEmpty() && childMap[parent].isNullOrEmpty()){ deleteItem(parent) }
    }

    override fun deleteItem(item: T){
        getParents(item).forEach { removeRelation(item, it) }
        getChildren(item).forEach { removeRelation(it, item) }

        parentMap.remove(item)
        childMap.remove(item)
    }
}
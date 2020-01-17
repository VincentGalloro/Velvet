package velvet.structs

open class Hierarchy<T : Any> {

    protected val pairs: MutableSet<Pair<T, T>> = mutableSetOf()
    protected val parentMap: MutableMap<T, MutableSet<T>> = mutableMapOf()
    protected val childMap: MutableMap<T, MutableSet<T>> = mutableMapOf()

    fun getPairs() = pairs.toList()
    fun getParents(item: T) = (parentMap[item] ?: mutableSetOf()).toList()
    fun getChildren(item: T) = (childMap[item] ?: mutableSetOf()).toList()

    fun addRelation(child: T, parent: T){
        if(child == parent) return
        if((child to parent) in pairs) return

        pairs.add(child to parent)

        parentMap.getOrPut(child, { mutableSetOf() }).add(parent)
        childMap.getOrPut(parent, { mutableSetOf() }).add(child)
    }

    fun removeRelation(child: T, parent: T){
        parentMap[child]?.remove(parent)
        childMap[parent]?.remove(child)
        pairs.remove(child to parent)
    }

    fun deleteItem(item: T){
        getParents(item).forEach { removeRelation(item, it) }
        getChildren(item).forEach { removeRelation(it, item) }

        parentMap.remove(item)
        childMap.remove(item)
    }
}
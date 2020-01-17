package velvet.structs

open class Tree<T : Any> {

    protected val items: MutableSet<T> = mutableSetOf()
    protected val parentMap: MutableMap<T, T> = mutableMapOf()
    protected val childMap: MutableMap<T, MutableSet<T>> = mutableMapOf()

    var root: T? = null
        set(value) {
            field = if (value == null){
                field?.let { if(deleteItem(it)) null else field }
            } else {
                field?.let { substituteItem(it, value) }
                items.add(value) //if substitute was called, this is redundant
                value
            }
        }

    fun getParent(item: T) = parentMap[item]
    fun getParentChain(item: T) = generateSequence(item, { getParent(it) }).toList().reversed()
    fun getChildren(item: T) = childMap.getOrElse(item, { mutableSetOf() }).toList()

    fun addItem(item: T, parent: T){
        if(item in items || parent !in items) return

        items.add(item)
        parentMap[item] = parent
        childMap.getOrPut(parent, { mutableSetOf() }).add(item)
    }

    fun substituteItem(oldItem: T, newItem: T){
        if(oldItem==newItem || oldItem !in items || newItem in items) return

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
    }

    fun canDelete(item: T): Boolean {
        if (item !in items) return false

        //item cannot have children
        if (childMap[item]?.isNotEmpty() == true) return false

        return true
    }

    fun deleteItem(item: T): Boolean{
        if(!canDelete(item)) return false

        items.remove(item)
        parentMap.remove(item)?.let {
            childMap[it]?.remove(item)
        }

        return true
    }
}
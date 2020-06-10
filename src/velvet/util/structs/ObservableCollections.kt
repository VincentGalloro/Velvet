package velvet.util.structs

class ObservableList<T> {

    private val _items: MutableList<T> = mutableListOf()
    var onAdd: ((T)->Unit)? = null
    var onRemove: ((T)->Unit)? = null

    val items: List<T> get() = _items.toList()

    fun add(item: T){
        _items.add(item)
        onAdd?.invoke(item)
    }

    fun remove(item: T){
        _items.remove(item)
        onRemove?.invoke(item)
    }
}

class ObservableSet<T>{

    private val _items: MutableSet<T> = mutableSetOf()
    var onAdd: ((T)->Unit)? = null
    var onRemove: ((T)->Unit)? = null

    val items: Set<T> get() = _items.toSet()

    fun add(item: T){
        _items.add(item)
        onAdd?.invoke(item)
    }

    fun remove(item: T){
        _items.remove(item)
        onRemove?.invoke(item)
    }
}
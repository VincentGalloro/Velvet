package velvet.structs

interface Grid<T>{

    operator fun get(pos: Position): T
    operator fun set(pos: Position, item: T)

    fun forEach(action: (Position, T)->Unit)
}

class DenseGrid<T>(val size: Position, initializer: (Position)->T) : Grid<T>{

    private val items: MutableList<T> = MutableList(size.area){ initializer(Position.fromIndex(it, size.x)) }

    override fun get(pos: Position) = items[pos.toIndex(size.x)]
    override fun set(pos: Position, item: T){ items[pos.toIndex(size.x)] = item }

    override fun forEach(action: (Position, T) -> Unit){ size.gridIterate { action(it, get(it)) } }
}

class SparseGrid<T>(private val defaultItem: ()->T) : Grid<T>{

    private val items: MutableMap<Position, T> = mutableMapOf()

    override fun get(pos: Position) = items[pos] ?: defaultItem()
    override fun set(pos: Position, item: T){ items[pos] = item }

    override fun forEach(action: (Position, T) -> Unit) { items.forEach { (t, u) -> action(t, u) } }
}
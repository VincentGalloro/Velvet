package velvet.util.types

import velvet.util.types.spatial.Position
import velvet.util.types.spatial.Size

interface Grid<T>{

    val size: Size

    fun toIndex(pos: Position) = size.toIndex(pos)
    fun fromIndex(index: Int) = size.fromIndex(index)

    operator fun get(pos: Position): T
    operator fun set(pos: Position, item: T)

    fun items(): Sequence<T>
    fun itemsIndexed(): Sequence<Pair<Position, T>>
}

class DenseGrid<T> private constructor(override val size: Size,
                                       val items: MutableList<T>) : Grid<T> {

    companion object{

        fun <T> ofSize(size: Size, initializer: (Position)->T)
                = DenseGrid(size, MutableList(size.calculateArea()) { initializer(size.fromIndex(it)) })

        operator fun <T> invoke(grid: Grid<T>)
                = DenseGrid(grid.size, grid.items().toMutableList())
    }

    override fun get(pos: Position) = items[size.toIndex(pos)]
    override fun set(pos: Position, item: T) { items[size.toIndex(pos)] = item }

    override fun items() = items.asSequence()
    override fun itemsIndexed() = items.indices.asSequence().map { size.fromIndex(it) to items[it] }
}

class SparseGrid<T> private constructor(override val size: Size,
                                        private val items: MutableMap<Position, T>,
                                        var defaultItem: T) : Grid<T> {
    companion object{
        fun <T> ofSize(size: Size, defaultItem: T) = SparseGrid(size, mutableMapOf(), defaultItem)
    }

    override fun get(pos: Position) = items[pos] ?: defaultItem
    override fun set(pos: Position, item: T){ items[pos] = item }

    override fun items() = items.asSequence().map { it.value }
    override fun itemsIndexed() = items.asSequence().map { it.toPair() }
}
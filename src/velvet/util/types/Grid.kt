package velvet.util.types

import velvet.util.types.spatial.Position
import velvet.util.types.spatial.Size
import kotlin.IllegalArgumentException

interface Grid<T>{

    val size: Size

    fun toIndex(pos: Position) = size.toIndex(pos)
    fun fromIndex(index: Int) = size.fromIndex(index)
    operator fun contains(pos: Position) = pos in size

    operator fun get(pos: Position): T?

    fun items(): Sequence<T?> = size.positions().map { get(it) }
    fun itemsIndexed() = size.positions().map { it to get(it) }
    fun nonNullItemsIndexed(): Sequence<Pair<Position, T>>
            = itemsIndexed().mapNotNull { it.second?.let { v -> it.first to v } }

    fun mergeOnto(base: Grid<T>): Grid<T>
}

interface MutableGrid<T> : Grid<T>{

    operator fun set(pos: Position, item: T?)
}

class DenseGrid<T> private constructor(override val size: Size,
                                       val items: MutableList<T?>) : MutableGrid<T> {

    companion object{
        fun <T> ofSize(size: Size, initializer: (Position)->T?)
                = DenseGrid(size, MutableList(size.area) { initializer(size.fromIndex(it)) })
    }

    override fun get(pos: Position): T?{
        if(pos !in this) return null
        return items[toIndex(pos)]
    }
    override fun set(pos: Position, item: T?) {
        if(pos !in this) return
        items[toIndex(pos)] = item
    }

    override fun items() = items.asSequence()

    override fun mergeOnto(base: Grid<T>): MutableGrid<T> {
        if(size != base.size) throw IllegalArgumentException("grid sizes must match")
        return ofSize(size){ get(it) ?: base[it] }
    }
}

class SparseGrid<T> private constructor(override val size: Size,
                                        private val items: MutableMap<Position, T>) : MutableGrid<T> {
    companion object{
        fun <T> ofSize(size: Size) = SparseGrid(size, mutableMapOf<Position, T>())
    }

    override fun get(pos: Position) = items[pos]
    override fun set(pos: Position, item: T?){
        if(pos !in this) return
        if(item != null) items[pos] = item
        else items.remove(pos)
    }

    override fun nonNullItemsIndexed() = items.toList().asSequence()

    override fun mergeOnto(base: Grid<T>): Grid<T> {
        if(size != base.size) throw IllegalArgumentException("grid sizes must match")
        return DenseGrid.ofSize(size){ get(it) ?: base[it] }
    }
}
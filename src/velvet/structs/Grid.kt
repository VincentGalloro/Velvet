package velvet.structs

class Grid<T>(val size: Position, initializer: (Position)->T) {

    private val items: MutableList<T> = MutableList(size.area){ initializer(Position.fromIndex(it, size.x)) }

    operator fun get(pos: Position) = items[pos.toIndex(size.x)]
    operator fun set(pos: Position, item: T){ items[pos.toIndex(size.x)] = item }
}
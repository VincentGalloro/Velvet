package velvet.util.types.spatial

class Region private constructor(val topLeft: Position, val size: Size) {

    companion object{
        operator fun invoke() = Region(Position(), Size())
        fun fromStartOfSize(position: Position, size: Size) = Region(position, size)
        fun fromStartToEnd(start: Position, end: Position) = Region(start, (end - start).size)
    }

    val bottomRight: Position get() = topLeft + Position(size.width, size.height)

    fun withinBounds(position: Position) = size.withinBounds(position-topLeft)

    fun toIndex(p: Position) = size.toIndex(p - topLeft)
    fun fromIndex(index: Int) = size.fromIndex(index) + topLeft

    fun containedPositions() = size.containedPositions().map { it + topLeft }

    fun randomWithin() = size.randomWithin() + topLeft

    override fun toString(): String {
        return "Region(topLeft=$topLeft, size=$size)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Region) return false

        if (topLeft != other.topLeft) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = topLeft.hashCode()
        result = 31 * result + size.hashCode()
        return result
    }
}

fun Sequence<Position>.toRegion(): Region {
    return Region.fromStartToEnd(
            Position(
                    asSequence().map { it.x }.min() ?: return Region(),
                    asSequence().map { it.y }.min() ?: return Region()
            ),
            Position(
                    asSequence().map { it.x }.max()?.inc() ?: return Region(),
                    asSequence().map { it.y }.max()?.inc() ?: return Region()
            )
    )
}
fun Iterable<Position>.toRegion(): Region = asSequence().toRegion()
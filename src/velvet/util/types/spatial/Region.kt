package velvet.util.types.spatial

import kotlin.random.Random

class Region(val topLeft: Position, val size: Size) {

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
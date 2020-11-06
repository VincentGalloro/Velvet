package velvet.util.types.spatial

import java.awt.Dimension
import kotlin.math.max
import kotlin.random.Random

class Size private constructor(val width: Int, val height: Int){

    companion object{
        operator fun invoke() = Size(0, 0)
        operator fun invoke(width: Int, height: Int) = Size(max(width, 0), max(height, 0))
        operator fun invoke(size: Int) = invoke(size, size)
    }

    //converters
    val position by lazy { Position(width, height) }
    val dimension by lazy { Dimension(width, height) }
    val area by lazy { Area(width, height) }
    val vector by lazy { Vector(width, height) }

    //properties
    fun calculateArea() = width*height
    val perimeter = (width+height)*2

    //utility
    fun withinBounds(p: Position) = p.x>=0 && p.y>=0 && p.x<width && p.y<height
    fun withinBounds(region: Region) = withinBounds(region.topLeft) && withinBounds(region.bottomRight-1)

    fun toIndex(p: Position) = (p.x + p.y*width)
    fun toIndexOrNull(p: Position) = toIndex(p).takeIf { withinBounds(p) }
    fun fromIndex(index: Int) = Position(index%width, index/width)

    fun containedPositions() = (0 until calculateArea()).asSequence().map(::fromIndex)

    fun randomWithin() = Position(Random.nextInt(width), Random.nextInt(height))

    override fun toString(): String {
        return "Size(width=$width, height=$height)"
    }

    fun toRegion(topLeft: Position = Position()) = Region.fromStartOfSize(topLeft, this)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Size) return false

        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        return result
    }
}

fun Dimension.toSize() = Size(width, height)
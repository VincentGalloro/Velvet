package velvet.util.types.spatial

import java.awt.Dimension
import kotlin.math.max
import kotlin.random.Random

class Area private constructor(val width: Double, val height: Double){

    companion object{
        operator fun invoke(width: Double, height: Double) = Area(max(width, 0.0), max(height, 0.0))
        operator fun invoke(width: Double, height: Int) = invoke(width, height.toDouble())
        operator fun invoke(width: Int, height: Double) = invoke(width.toDouble(), height)
        operator fun invoke(width: Int, height: Int) = invoke(width.toDouble(), height.toDouble())
        operator fun invoke(size: Double) = invoke(size, size)
        operator fun invoke(size: Int) = invoke(size.toDouble(), size.toDouble())

        val ZERO = Area(0)
        val UNIT = Area(1)
    }

    //converters
    val vector by lazy { Vector(width, height) }

    //properties
    val area = width*height
    val perimeter = (width+height)*2

    //utility
    fun contains(v: Vector) = v.x>=0 && v.y>=0 && v.x<=width && v.y<=height

    fun randomWithin() = Vector(Random.nextDouble()*width, Random.nextDouble()*height)

    fun getPos(anchor: Vector) = vector * anchor
    fun getAnchor(pos: Vector) = pos / vector

    override fun toString(): String {
        return "Area(width=$width, height=$height)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Area) return false

        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }
}

fun Dimension.toArea() = Area(width, height)
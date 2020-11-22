package velvet.util.types.spatial

import java.awt.Dimension
import kotlin.math.max
import kotlin.random.Random

class Area private constructor(val width: Double, val height: Double) : TwoDimensionalDouble{

    companion object{
        operator fun invoke(width: Double, height: Double) = Area(max(width, 0.0), max(height, 0.0))
        operator fun invoke(width: Double, height: Int) = invoke(width, height.toDouble())
        operator fun invoke(width: Int, height: Double) = invoke(width.toDouble(), height)
        operator fun invoke(width: Int, height: Int) = invoke(width.toDouble(), height.toDouble())
        operator fun invoke(size: Double) = invoke(size, size)
        operator fun invoke(size: Int) = invoke(size.toDouble(), size.toDouble())
        operator fun invoke() = Area(0.0, 0.0)
    }

    override val xComponent get() = width
    override val yComponent get() = height

    override fun toArea() = this

    //properties
    val area = width*height
    val perimeter = (width+height)*2

    //utility
    operator fun contains(v: Vector) = v.x>=0 && v.y>=0 && v.x<=width && v.y<=height

    fun randomWithin() = Vector(Random.nextDouble(), Random.nextDouble())*this

    fun getPos(anchor: Vector) = vector * anchor
    fun getAnchor(pos: Vector) = pos / vector

    override fun fold(td: TwoDimensionalDouble, op: (Double, Double) -> Double)
            = invoke(op(width, td.xComponent), op(height, td.yComponent))
    override fun fold(td: TwoDimensionalInt, op: (Double, Int) -> Double)
            = invoke(op(width, td.xComponent), op(height, td.yComponent))
    override fun map(op: (Double) -> Double) = invoke(op(width), op(height))

    operator fun plus(td: TwoDimensionalDouble) = fold(td){ a, b -> a+b }
    operator fun plus(td: TwoDimensionalInt) = fold(td){ a, b -> a+b }
    operator fun plus(td: Double) = map { it+td }
    operator fun plus(td: Int) = map { it+td }

    operator fun minus(td: TwoDimensionalDouble) = fold(td){ a, b -> a-b }
    operator fun minus(td: TwoDimensionalInt) = fold(td){ a, b -> a-b }
    operator fun minus(td: Double) = map { it-td }
    operator fun minus(td: Int) = map { it-td }

    operator fun times(td: TwoDimensionalDouble) = fold(td){ a, b -> a*b }
    operator fun times(td: TwoDimensionalInt) = fold(td){ a, b -> a*b }
    operator fun times(td: Double) = map { it*td }
    operator fun times(td: Int) = map { it*td }

    operator fun div(td: TwoDimensionalDouble) = fold(td){ a, b -> a/b }
    operator fun div(td: TwoDimensionalInt) = fold(td){ a, b -> a/b }
    operator fun div(td: Double) = map { it/td }
    operator fun div(td: Int) = map { it/td }

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
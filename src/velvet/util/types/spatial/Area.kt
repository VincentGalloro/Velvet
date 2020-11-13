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

    //properties
    val area = width*height
    val perimeter = (width+height)*2

    //utility
    operator fun contains(v: Vector) = v.x>=0 && v.y>=0 && v.x<=width && v.y<=height

    fun randomWithin() = Vector(Random.nextDouble(), Random.nextDouble())*this

    fun getPos(anchor: Vector) = vector * anchor
    fun getAnchor(pos: Vector) = pos / vector

    operator fun plus(td: TwoDimensionalDouble) = invoke(width+td.xComponent, height+td.yComponent)
    operator fun plus(td: TwoDimensionalInt) = invoke(width+td.xComponent, height+td.yComponent)
    operator fun plus(td: Double) = invoke(width+td, height+td)
    operator fun plus(td: Int) = invoke(width+td, height+td)

    operator fun minus(td: TwoDimensionalDouble) = invoke(width-td.xComponent, height-td.yComponent)
    operator fun minus(td: TwoDimensionalInt) = invoke(width-td.xComponent, height-td.yComponent)
    operator fun minus(td: Double) = invoke(width-td, height-td)
    operator fun minus(td: Int) = invoke(width-td, height-td)

    operator fun times(td: TwoDimensionalDouble) = invoke(width*td.xComponent, height*td.yComponent)
    operator fun times(td: TwoDimensionalInt) = invoke(width*td.xComponent, height*td.yComponent)
    operator fun times(td: Double) = invoke(width*td, height*td)
    operator fun times(td: Int) = invoke(width*td, height*td)

    operator fun div(td: TwoDimensionalDouble) = invoke(width/td.xComponent, height/td.yComponent)
    operator fun div(td: TwoDimensionalInt) = invoke(width/td.xComponent, height/td.yComponent)
    operator fun div(td: Double) = invoke(width/td, height/td)
    operator fun div(td: Int) = invoke(width/td, height/td)

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
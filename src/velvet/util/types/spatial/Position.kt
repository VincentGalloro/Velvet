package velvet.util.types.spatial

import java.awt.Point
import kotlin.math.atan2
import kotlin.math.sqrt

data class Position(val x: Int, val y: Int) : TwoDimensionalInt{

    companion object {
        val UP = Position(0, -1)
        val RIGHT = Position(1, 0)
        val DOWN = Position(0, 1)
        val LEFT = Position(-1, 0)

        val DIRS = listOf(UP, RIGHT, DOWN, LEFT)
        private val DIAGONALS = listOf(Position(-1, -1), Position(1, -1), Position(1, 1), Position(-1, 1))
        val FULL_DIRS = DIRS + DIAGONALS
    }

    constructor(p: Int) : this(p, p)
    constructor(): this(0, 0)

    override val xComponent get() = x
    override val yComponent get() = y

    val angle get() = atan2(y.toDouble(), x.toDouble())
    val magnitude get() = sqrt(square().sum.toDouble())
    fun abs() = Position(kotlin.math.abs(x), kotlin.math.abs(y))
    fun square() = this*this
    fun flip() = Position(y, x)

    fun angleTo(td: TwoDimensionalInt) = (-(this-td)).angle
    fun angleFrom(td: TwoDimensionalInt) = (this-td).angle
    fun distanceTo(td: TwoDimensionalInt) = (this-td).magnitude

    fun angleTo(td: TwoDimensionalDouble) = (-(this-td)).angle
    fun angleFrom(td: TwoDimensionalDouble) = (this-td).angle
    fun distanceTo(td: TwoDimensionalDouble) = (this-td).magnitude

    fun gridDistanceTo(p: Position) = (this-p).abs().sum

    fun gridNeighbours() = DIRS.map { this+it }
    fun fullNeighbours() = FULL_DIRS.map { this+it }

    operator fun plus(td: TwoDimensionalInt) = Position(x+td.xComponent, y+td.yComponent)
    operator fun plus(td: TwoDimensionalDouble) = Vector(x+td.xComponent, y+td.yComponent)
    operator fun plus(td: Int) = Position(x+td, y+td)
    operator fun plus(td: Double) = Vector(x+td, y+td)

    operator fun minus(td: TwoDimensionalInt) = Position(x-td.xComponent, y-td.yComponent)
    operator fun minus(td: TwoDimensionalDouble) = Vector(x-td.xComponent, y-td.yComponent)
    operator fun minus(td: Int) = Position(x-td, y-td)
    operator fun minus(td: Double) = Vector(x-td, y-td)

    operator fun times(td: TwoDimensionalInt) = Position(x*td.xComponent, y*td.yComponent)
    operator fun times(td: TwoDimensionalDouble) = Vector(x*td.xComponent, y*td.yComponent)
    operator fun times(td: Int) = Position(x*td, y*td)
    operator fun times(td: Double) = Vector(x*td, y*td)

    operator fun div(td: TwoDimensionalInt) = Position(x/td.xComponent, y/td.yComponent)
    operator fun div(td: TwoDimensionalDouble) = Vector(x/td.xComponent, y/td.yComponent)
    operator fun div(td: Int) = Position(x/td, y/td)
    operator fun div(td: Double) = Vector(x/td, y/td)

    operator fun unaryMinus() = Position(-x, -y)
}

fun Point.toPosition() = Position(x, y)
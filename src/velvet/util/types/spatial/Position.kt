package velvet.util.types.spatial

import java.awt.Point
import kotlin.math.atan2
import kotlin.math.sqrt

data class Position(val x: Int, val y: Int) : TwoDimensionalInt{

    companion object {
        private val UP = Position(0, -1)
        private val RIGHT = Position(1, 0)
        private val DOWN = Position(0, 1)
        private val LEFT = Position(-1, 0)

        private val DIRS = listOf(UP, RIGHT, DOWN, LEFT)
        private val DIAGONALS = listOf(Position(-1, -1), Position(1, -1), Position(1, 1), Position(-1, 1))
        private val FULL_DIRS = DIRS + DIAGONALS
    }

    constructor(p: Int) : this(p, p)
    constructor(): this(0, 0)

    override val xComponent get() = x
    override val yComponent get() = y

    override val position get() = this

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

    override fun fold(td: TwoDimensionalInt, op: (Int, Int) -> Int)
            = Position(op(x, td.xComponent), op(y, td.yComponent))
    override fun map(op: (Int) -> Int) = Position(op(x), op(y))

    operator fun plus(td: TwoDimensionalInt) = fold(td){ a, b -> a+b }
    operator fun plus(td: TwoDimensionalDouble) = td.vector + this
    operator fun plus(td: Int) = map { it+td }
    operator fun plus(td: Double) = vector + td

    operator fun minus(td: TwoDimensionalInt) = fold(td){ a, b -> a-b }
    operator fun minus(td: TwoDimensionalDouble) = td.vector - this
    operator fun minus(td: Int) = map { it-td }
    operator fun minus(td: Double) = vector - td

    operator fun times(td: TwoDimensionalInt) = fold(td){ a, b -> a*b }
    operator fun times(td: TwoDimensionalDouble) = td.vector * this
    operator fun times(td: Int) = map { it*td }
    operator fun times(td: Double) = vector * td

    operator fun div(td: TwoDimensionalInt) = fold(td){ a, b -> a/b }
    operator fun div(td: TwoDimensionalDouble) = td.vector / this
    operator fun div(td: Int) = map { it/td }
    operator fun div(td: Double) = vector / td

    operator fun unaryMinus() = this*-1
}

fun Point.toPosition() = Position(x, y)
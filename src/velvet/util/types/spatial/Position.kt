package velvet.util.types.spatial

import java.awt.Point
import kotlin.math.sqrt
import kotlin.random.Random

class Position(val x: Int, val y: Int) {

    companion object {
        val UP = Position(0, -1)
        val RIGHT = Position(1, 0)
        val DOWN = Position(0, 1)
        val LEFT = Position(-1, 0)

        val HORIZONTAL = listOf(LEFT, RIGHT)
        val VERTICAL = listOf(UP, DOWN)
        val DIRS = HORIZONTAL + VERTICAL
        val DIAGONALS = listOf(Position(-1, -1), Position(1, -1), Position(1, 1), Position(-1, 1))
        val FULL_DIRS = DIRS + DIAGONALS
    }

    constructor(p: Int) : this(p, p)
    constructor(): this(0, 0)

    //converters
    val vector by lazy { Vector(x, y) }
    val size by lazy { Size(x, y) }

    //properties
    val angle by lazy { flip().reduce(Math::atan2) }
    val magnitude by lazy { sqrt(square().sum.toDouble()) }
    val max by lazy { kotlin.math.max(x,y) }
    val min by lazy { kotlin.math.min(x,y) }
    val sum by lazy { x+y }

    //convenience
    fun flip() = Vector(y, x)
    fun square() = this*this
    fun abs() = apply { kotlin.math.abs(it) }

    fun distanceTo(p: Position) = (this-p).magnitude
    fun gridDistanceTo(p: Position) = (this-p).abs().sum

    //helpers
    fun apply(func: (Int)->Int) = Position(func(x), func(y))
    fun combine(func: (Int, Int)->Int, other: Position) = Position(func(x, other.x), func(y, other.y))

    fun <T> reduce(func: (Int, Int)->T): T = func(x, y)

    //operators
    operator fun plus(p: Position) = combine(Int::plus, p)
    operator fun plus(i: Int) = apply{ it+i }

    operator fun minus(p: Position) = combine(Int::minus, p)
    operator fun minus(i: Int) = apply{ it-i }
    operator fun unaryMinus() = apply { -it }

    operator fun times(p: Position) = combine(Int::times, p)
    operator fun times(i: Int) = apply { it*i }

    operator fun div(p: Position) = combine(Int::div, p)
    operator fun div(i: Int) = apply { it/i }

    override fun toString(): String {
        return "Position(x=$x, y=$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Position) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

fun Point.toPosition() = Point(x, y)
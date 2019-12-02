package velvet.structs

import java.awt.Dimension
import java.awt.Point
import kotlin.math.sqrt

data class Position constructor(val x: Int = 0, val y: Int = 0) {

    companion object {
        val UP = Position(0, -1)
        val RIGHT = Position(1, 0)
        val DOWN = Position(0, 1)
        val LEFT = Position(-1, 0)
        val DIRS = arrayOf(UP, RIGHT, DOWN, LEFT)
    }

    val square by lazy { apply { it*it } }
    val magnitude by lazy { sqrt(square.sum.toDouble()) }

    val vector by lazy { Vector(x, y) }
    val point by lazy { Point(x, y) }
    val dimension by lazy { Dimension(x, y) }

    val max by lazy { kotlin.math.max(x,y) }
    val min by lazy { kotlin.math.min(x,y) }
    val sum by lazy { x+y }

    val flip by lazy { Position(y, x) }

    fun apply(func: (Int)->Int) = Position(func(x), func(y))
    fun apply(func: (Int, Int)->Int, other: Position) = Position(func(x, other.x), func(y, other.y))

    fun combine(func: (Int, Int)->Int) = func(x, y)

    operator fun plus(p: Position) = apply(Int::plus, p)
    operator fun plus(i: Int) = apply{ it+i }

    operator fun minus(p: Position) = apply(Int::minus, p)
    operator fun minus(i: Int) = apply{ it-i }
    operator fun unaryMinus() = apply { -it }

    operator fun times(p: Position) = apply(Int::times, p)
    operator fun times(i: Int) = apply { it*i }

    operator fun div(p: Position) = apply(Int::div, p)
    operator fun div(i: Int) = apply { it/i }

    fun abs() = apply{ kotlin.math.abs(it) }

    fun move(dir: Int, amount: Int = 1) = this + DIRS[dir]*amount

    fun gridIterate(consumer: (Position)->Unit) {
        for (y in 0 until y) {
            for (x in 0 until x) {
                consumer(Position(x, y))
            }
        }
    }

    fun getGridDistance(p: Position) = (this-p).abs().sum
    fun getDistance(p: Position) = (this-p).magnitude

    fun lessThan(p: Position) = x <= p.x && y <= p.y
    fun greaterThan(p: Position) = x >= p.x && y >= p.y
}
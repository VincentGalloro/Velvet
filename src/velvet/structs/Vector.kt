package velvet.structs

import java.awt.Point
import java.awt.geom.AffineTransform
import java.awt.geom.NoninvertibleTransformException
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vector(val x: Double, val y: Double) {

    companion object {
        fun unitVector(angle: Double): Vector {
            return Vector(cos(angle), sin(angle))
        }
    }

    constructor(v: Double) : this(v, v)
    constructor(v: Int) : this(v, v)
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
    constructor() : this(0.0, 0.0)

    val angle by lazy { flip.combine(Math::atan2) }
    val square by lazy { apply { it*it } }
    val magnitude by lazy { sqrt(square.sum) }

    val position by lazy { Position(x.toInt(), y.toInt()) }
    val point by lazy { Point(x.toInt(), y.toInt()) }

    val max by lazy { kotlin.math.max(x,y) }
    val min by lazy { kotlin.math.min(x,y) }
    val sum by lazy { x+y }

    val flip by lazy { Vector(y, x) }

    fun apply(func: (Double)->Double) = Vector(func(x), func(y))
    fun apply(func: (Double, Double)->Double, other: Vector) = Vector(func(x, other.x), func(y, other.y))

    fun combine(func: (Double, Double)->Double) = func(x, y)

    operator fun plus(v: Vector) = apply(Double::plus, v)
    operator fun plus(d: Double) = apply{ it+d }
    operator fun plus(d: Int) = apply{ it+d }

    operator fun minus(v: Vector) = apply(Double::minus, v)
    operator fun minus(d: Double) = apply{ it-d }
    operator fun minus(d: Int) = apply{ it-d }
    operator fun unaryMinus() = apply { -it }

    operator fun times(v: Vector) = apply(Double::times, v)
    operator fun times(d: Double) = apply { it*d }
    operator fun times(d: Int) = apply { it*d }

    operator fun div(v: Vector) = apply(Double::div, v)
    operator fun div(d: Double) = apply { it/d }
    operator fun div(d: Int) = apply { it/d }

    fun round() = apply { kotlin.math.round(it) }
    fun floor() = apply { kotlin.math.floor(it) }
    fun ceil() = apply { kotlin.math.ceil(it) }
    fun abs() = apply { kotlin.math.abs(it) }

    fun max(v: Vector) = apply({ a,b -> kotlin.math.max(a,b) }, v)
    fun min(v: Vector) = apply({ a,b -> kotlin.math.min(a,b) }, v)

    fun move(angle: Double, amount: Double) = this + unitVector(angle)*amount
    fun getAngle(v: Vector) = (v-this).angle

    fun getDistance(v: Vector) = sqrt(getDistSqr(v))
    fun getDistSqr(v: Vector) = (this-v).square.sum

    fun getDirection(): Int{
        if(kotlin.math.abs(y) > kotlin.math.abs(x)){
            return if(y > 0) 2 else 0
        }
        return if(x < 0) 3 else 1
    }

    fun rotate(c: Vector, a: Double) = (this-c).rotate(a)+c
    fun rotate(a: Double) = unitVector(a) * x + unitVector(a+Math.PI/2) * y

    operator fun compareTo(v: Vector): Int{
        if(x < v.x && y < v.y) return -1
        if(x > v.x && y > v.y) return 1
        return 0
    }

    fun transform(at: AffineTransform): Vector {
        val d = doubleArrayOf(x, y)
        at.transform(d, 0, d, 0, 1)
        return Vector(d[0], d[1])
    }

    fun inverseTransform(at: AffineTransform): Vector {
        val d = doubleArrayOf(x, y)
        try {
            at.inverseTransform(d, 0, d, 0, 1)
        } catch (ignored: NoninvertibleTransformException) { }
        return Vector(d[0], d[1])
    }
}

fun Point.toVector() = Vector(x, y)
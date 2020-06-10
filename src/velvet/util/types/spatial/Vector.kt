package velvet.util.types.spatial

import java.awt.Point
import java.awt.geom.AffineTransform
import java.awt.geom.NoninvertibleTransformException
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Vector(val x: Double, val y: Double) {

    companion object {
        val ZERO = Vector(0)
        val HALF = Vector(0.5)
        val ONE = Vector(1)

        fun unitVector(angle: Double) = Vector(cos(angle), sin(angle))
    }

    constructor(v: Double) : this(v, v)
    constructor(v: Int) : this(v.toDouble(), v.toDouble())
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
    constructor(x: Double, y: Int) : this(x, y.toDouble())
    constructor(x: Int, y: Double) : this(x.toDouble(), y)
    constructor() : this(0.0, 0.0)

    //converters
    val position by lazy { Position(x.toInt(), y.toInt()) }
    val area by lazy { Area(x, y) }

    //properties
    val angle by lazy { flip().reduce(Math::atan2) }
    val magnitude by lazy { sqrt(square().sum) }
    val max by lazy { kotlin.math.max(x,y) }
    val min by lazy { kotlin.math.min(x,y) }
    val sum by lazy { x+y }

    //convenience
    fun flip() = Vector(y, x)
    fun square() = this*this

    fun round() = apply { kotlin.math.round(it) }
    fun floor() = apply { kotlin.math.floor(it) }
    fun ceil() = apply { kotlin.math.ceil(it) }
    fun abs() = apply { kotlin.math.abs(it) }

    fun move(angle: Double, amount: Double) = this + unitVector(angle) *amount
    fun angleTo(v: Vector) = (v-this).angle
    fun angleFrom(v: Vector) = (this-v).angle
    fun distanceTo(v: Vector) = (this-v).magnitude
    fun rotateAround(c: Vector, a: Double) = (this-c).rotate(a)+c
    fun rotate(a: Double) = unitVector(angle + a) * magnitude

    //helpers
    fun apply(func: (Double)->Double) = Vector(func(x), func(y))
    fun combine(func: (Double, Double)->Double, other: Vector) = Vector(func(x, other.x), func(y, other.y))

    fun <T> reduce(func: (Double, Double)->T): T = func(x, y)

    //operators
    operator fun plus(v: Vector) = combine(Double::plus, v)
    operator fun plus(d: Double) = apply{ it+d }
    operator fun plus(d: Int) = apply{ it+d }

    operator fun minus(v: Vector) = combine(Double::minus, v)
    operator fun minus(d: Double) = apply{ it-d }
    operator fun minus(d: Int) = apply{ it-d }
    operator fun unaryMinus() = apply { -it }

    operator fun times(v: Vector) = combine(Double::times, v)
    operator fun times(d: Double) = apply { it*d }
    operator fun times(d: Int) = apply { it*d }

    operator fun div(v: Vector) = combine(Double::div, v)
    operator fun div(d: Double) = apply { it/d }
    operator fun div(d: Int) = apply { it/d }

    //affine transforms
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

    override fun toString(): String {
        return "Vector(x=$x, y=$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vector) return false

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}

fun Point.toVector() = Vector(x, y)
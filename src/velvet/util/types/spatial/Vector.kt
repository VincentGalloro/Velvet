package velvet.util.types.spatial

import kotlin.math.*

data class Vector(val x: Double,
                  val y: Double): TwoDimensionalDouble {

    companion object {
        val HALF = Vector(0.5)
        val ONE = Vector(1)

        fun unit(angle: Double) = Vector(cos(angle), sin(angle))
    }

    constructor(x: Double, y: Int) : this(x, y.toDouble())
    constructor(x: Int, y: Double) : this(x.toDouble(), y)
    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
    constructor(v: Double) : this(v, v)
    constructor(v: Int) : this(v.toDouble(), v.toDouble())
    constructor() : this(0.0, 0.0)

    override val xComponent get() = x
    override val yComponent get() = y

    override val vector get() = this

    val angle get() = atan2(yComponent, xComponent)
    val magnitude get() = sqrt(square().sum)
    fun abs() = Vector(abs(x), abs(y))
    fun square() = this*this
    fun flip() = Vector(y, x)

    fun move(angle: Double, amount: Double) = this + unit(angle)*amount

    fun angleTo(td: TwoDimensionalInt) = (-(this-td)).angle
    fun angleFrom(td: TwoDimensionalInt) = (this-td).angle
    fun distanceTo(td: TwoDimensionalInt) = (this-td).magnitude

    fun angleTo(td: TwoDimensionalDouble) = (-(this-td)).angle
    fun angleFrom(td: TwoDimensionalDouble) = (this-td).angle
    fun distanceTo(td: TwoDimensionalDouble) = (this-td).magnitude

    fun rotateAround(c: TwoDimensionalInt, a: Double) = (this-c).rotate(a) + c
    fun rotateAround(c: TwoDimensionalDouble, a: Double) = (this-c).rotate(a) + c
    fun rotate(a: Double) = unit(angle + a) * magnitude

    override fun fold(td: TwoDimensionalDouble, op: (Double, Double) -> Double)
            = Vector(op(x, td.xComponent), op(y, td.yComponent))
    override fun fold(td: TwoDimensionalInt, op: (Double, Int) -> Double)
            = Vector(op(x, td.xComponent), op(y, td.yComponent))
    override fun map(op: (Double) -> Double) = Vector(op(x), op(y))

    operator fun plus(td: TwoDimensionalInt) = fold(td){ a, b -> a+b }
    operator fun plus(td: TwoDimensionalDouble) = fold(td){ a, b -> a+b }
    operator fun plus(td: Int) = map { it+td }
    operator fun plus(td: Double) = map { it+td }

    operator fun minus(td: TwoDimensionalInt) = fold(td){ a, b -> a-b }
    operator fun minus(td: TwoDimensionalDouble) = fold(td){ a, b -> a-b }
    operator fun minus(td: Int) = map { it-td }
    operator fun minus(td: Double) = map { it-td }

    operator fun times(td: TwoDimensionalInt) = fold(td){ a, b -> a*b }
    operator fun times(td: TwoDimensionalDouble) = fold(td){ a, b -> a*b }
    operator fun times(td: Int) = map { it*td }
    operator fun times(td: Double) = map { it*td }

    operator fun div(td: TwoDimensionalInt) = fold(td){ a, b -> a/b }
    operator fun div(td: TwoDimensionalDouble) = fold(td){ a, b -> a/b }
    operator fun div(td: Int) = map { it/td }
    operator fun div(td: Double) = map { it/td }

    operator fun unaryMinus() = this*-1
}
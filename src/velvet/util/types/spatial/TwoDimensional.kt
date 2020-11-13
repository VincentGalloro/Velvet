package velvet.util.types.spatial

import java.awt.Dimension
import java.awt.Point
import kotlin.math.*

interface TwoDimensionalInt {
    val xComponent: Int
    val yComponent: Int

    val point: Point get() = Point(xComponent, yComponent)
    val dimension: Dimension get() = Dimension(xComponent, yComponent)

    val vector: Vector get() = Vector(xComponent, yComponent)
    val position: Position get() = Position(xComponent, yComponent)
    fun toSize(): Size = Size(xComponent, yComponent)
    fun toArea(): Area = Area(xComponent, yComponent)

    val sum get() = xComponent + yComponent
    val min get() = min(xComponent, yComponent)
    val max get() = max(xComponent, yComponent)
}

interface TwoDimensionalDouble {
    val xComponent: Double
    val yComponent: Double

    val vector: Vector get() = Vector(xComponent, yComponent)
    fun toPosition(): Position = Position(xComponent.roundToInt(), yComponent.roundToInt())
    fun toSize(): Size = Size(ceil(xComponent).toInt(), ceil(yComponent).toInt())
    fun toArea(): Area = Area(xComponent, yComponent)

    val sum get() = xComponent + yComponent
    val min get() = min(xComponent, yComponent)
    val max get() = max(xComponent, yComponent)
}
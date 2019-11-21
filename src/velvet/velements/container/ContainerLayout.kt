package velvet.velements.container

import velvet.structs.Vector
import kotlin.math.abs

data class ContainerLayout(val pos: Vector = Vector.ZERO,
                           val size: Vector = Vector.ZERO,
                           val angle: Double = 0.0){

    fun relativeMove(move: Vector) = copy(pos=pos.add(move.rotate(angle)))

    fun padOut(padding: Double) = copy(size=size.add(padding*2))
    fun padIn(padding: Double) = copy(size=size.subtract(padding*2).max(Vector.ZERO))

    fun scale(scale: Double) = copy(size=size.multiply(scale))
    fun scale(scale: Vector) = copy(size=size.multiply(scale))

    fun horizontalSplit(startPercent: Double, endPercent: Double): ContainerLayout{
        return relativeMove(Vector(((endPercent+startPercent)*0.5-0.5)*size.x, 0.0))
                .copy(size=size.multiply(Vector(abs(endPercent - startPercent), 1.0)))
    }
    fun verticalSplit(startPercent: Double, endPercent: Double): ContainerLayout{
        return relativeMove(Vector(0.0, ((endPercent+startPercent)*0.5-0.5)*size.y))
                .copy(size=size.multiply(Vector(1.0, abs(endPercent - startPercent))))
    }

    fun fixRatio(ratio: Vector): ContainerLayout{
        return copy(size = ratio.multiply(size.divide(ratio).combine(Math::min)))
    }

    fun rotate(origin: Vector, rotation: Double): ContainerLayout{
        return copy(pos=pos.rotate(origin, rotation), angle=angle+rotation)
    }
}
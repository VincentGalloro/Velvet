package velvet.structs

import velvet.velements.VElement

data class Bounds(val start: Vector = Vector(),
                  val end: Vector = Vector()) {

    constructor(pos: Vector) : this(pos, pos)

    val size by lazy { end-start }

    operator fun plus(v: Vector) = Bounds(start+v, end+v)
    operator fun times(v: Vector) = Bounds(start*v, end*v)

    fun contains(v: Vector) = v.greaterThan(start) && v.lessThan(end)

    fun getPos(anchor: Vector) = start + size*anchor

    fun resize(size: Vector, anchor: Vector = Vector()): Bounds{
        val p = getPos(anchor)
        return Bounds(p - size*anchor, p - size*anchor + size)
    }
    fun scale(scale: Vector, anchor: Vector = Vector(0.5)) = resize(size*scale, anchor)
    fun fixRatio(ratio: Vector, anchor: Vector = Vector(0.5)) = resize(ratio * (size/ratio).min, anchor)
    fun fixRatioElement(element: VElement?, anchor: Vector = Vector(0.5)) = fixRatio(element?.size ?: Vector(1), anchor)

    fun subBounds(range: Bounds) = range*size + start
}
package velvet.structs

import velvet.vcontainer.VContainer
import velvet.vcontainer.velement.VElement

class Bounds private constructor(val center: Vector,
                                 val size: Vector,
                                 val angle: Double) {

    companion object{
        operator fun invoke() = Bounds(Vector(), Vector(), 0.0)

        fun fromStartToEnd(start: Vector, end: Vector) = Bounds((start+end)/2, end-start, 0.0)

        fun fromStartOfSize(start: Vector, size: Vector) = Bounds(start + size/2, size, 0.0)
        fun fromCenterOfSize(center: Vector, size: Vector) = Bounds(center, size, 0.0)
    }

    //helpers
    fun getPos(anchor: Vector) = center + ((anchor - 0.5)*size).rotate(angle)
    fun getAnchor(pos: Vector) = (pos - center).rotate(-angle) / size + 0.5

    fun contains(v: Vector) = (v - center).rotate(-angle).abs() < size/2

    //modifiers
    fun setPos(p: Vector) = Bounds(p, size, angle)
    fun localMove(p: Vector) = setPos(center + p.rotate(angle))
    fun globalMove(p: Vector) = setPos(center + p)

    fun setSize(s: Vector, anchor: Vector) = getPos(anchor).let { ap -> Bounds(center, s, angle).let { r -> r.globalMove(ap - r.getPos(anchor)) } }
    fun setWidth(w: Double, anchor: Double) = setSize(Vector(w, size.y), Vector(anchor, 0.0))
    fun setHeight(h: Double, anchor: Double) = setSize(Vector(size.x, h), Vector(0.0, anchor))
    fun resize(s: Vector, anchor: Vector) = setSize(size + s, anchor)
    fun scale(scale: Vector, anchor: Vector) = setSize(size*scale, anchor)
    fun fixRatio(ratio: Vector, anchor: Vector) = setSize(ratio * (size/ratio).min, anchor)
    fun fixRatioElement(vElement: VElement?, anchor: Vector = Vector(0.5)) =
            fixRatio(vElement?.size ?: Vector(1), anchor)

    fun rotate(a: Double) = Bounds(center, size, angle + a)
    fun setAngle(a: Double) = Bounds(center, size, a)
}
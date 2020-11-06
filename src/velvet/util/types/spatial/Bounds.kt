package velvet.util.types.spatial

import velvet.ui.velements.VElement

class Bounds private constructor(val center: Vector,
                                 val area: Area,
                                 val angle: Double) {

    companion object{
        operator fun invoke() = Bounds(Vector.ZERO, Area.ZERO, 0.0)

        fun fromStartToEnd(start: Vector, end: Vector) = Bounds((start + end) / 2, (end - start).area, 0.0)

        fun fromStartOfArea(start: Vector, area: Area) = Bounds(start + (area.vector / 2), area, 0.0)
        fun fromCenterOfArea(center: Vector, area: Area) = Bounds(center, area, 0.0)
    }

    //helpers
    fun toRelativePos(pos: Vector) = (pos - center).rotate(-angle) + area.vector/2
    fun toGlobalPos(relativePos: Vector) = (relativePos - area.vector/2).rotate(angle) + center

    fun getPos(anchor: Vector) = toGlobalPos(area.getPos(anchor))
    fun getAnchor(pos: Vector) = area.getAnchor(toRelativePos(pos))

    fun contains(pos: Vector) = area.contains(toRelativePos(pos))
    fun intersects(b: Bounds)
            = ((area.vector + b.area.vector)/2).area.contains((center - b.center).abs())

    //modifiers
    fun setPos(p: Vector, anchor: Vector) = globalMove(p - getPos(anchor))
    fun setCenter(p: Vector) = Bounds(p, area, angle)
    fun localMove(p: Vector) = setCenter(center + p.rotate(angle))
    fun globalMove(p: Vector) = setCenter(center + p)

    fun setArea(s: Area, anchor: Vector) = Bounds(center, s, angle).setPos(getPos(anchor), anchor)
    fun setWidth(w: Double, anchor: Double) = setArea(Area(w, area.height), Vector(anchor, 0))
    fun setHeight(h: Double, anchor: Double) = setArea(Area(area.width, h), Vector(0, anchor))

    fun resize(s: Area, anchor: Vector) = resize(s.vector, anchor)
    fun resize(s: Vector, anchor: Vector) = setArea((area.vector + s).area, anchor)
    fun resizeWidth(w: Double, anchor: Double) = setWidth(area.width + w, anchor)
    fun resizeHeight(h: Double, anchor: Double) = setHeight(area.height + h, anchor)

    fun scale(scale: Vector, anchor: Vector) = setArea((area.vector*scale).area, anchor)
    fun scaleWidth(scale: Double, anchor: Double) = setWidth(area.width*scale, anchor)
    fun scaleHeight(scale: Double, anchor: Double) = setHeight(area.height*scale, anchor)

    fun fixRatio(ratio: Vector, anchor: Vector) = setArea((ratio * (area.vector/ratio).min).area, anchor)
    fun fixRatioElement(vElement: VElement, anchor: Vector = Vector.HALF) =
            fixRatio(vElement.area?.vector ?: Vector.ONE, anchor)

    fun rotate(a: Double) = Bounds(center, area, angle + a)
    fun setAngle(a: Double) = Bounds(center, area, a)


    override fun toString(): String {
        return "Bounds(center=$center, area=$area, angle=$angle)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Bounds) return false

        if (center != other.center) return false
        if (area != other.area) return false
        if (angle != other.angle) return false

        return true
    }

    override fun hashCode(): Int {
        var result = center.hashCode()
        result = 31 * result + area.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }
}
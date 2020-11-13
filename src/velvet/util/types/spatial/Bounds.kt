package velvet.util.types.spatial

import velvet.ui.velements.VElement

data class Bounds(val center: Vector,
                  val size: Area,
                  val angle: Double) {

    companion object{
        operator fun invoke() = Bounds(Vector(), Area(), 0.0)

        fun fromStartToEnd(start: Vector, end: Vector)
                = Bounds((start + end) / 2, (end - start).toArea(), 0.0)
        fun fromStartOfSize(start: Vector, size: Area)
                = Bounds(start + (size / 2), size, 0.0)
        fun fromCenterOfSize(center: Vector, size: Area)
                = Bounds(center, size, 0.0)
    }

    //helpers
    fun toRelativePos(pos: Vector) = (pos - center).rotate(-angle) + size/2
    fun toGlobalPos(relativePos: Vector) = (relativePos - size/2).rotate(angle) + center

    fun getPos(anchor: Vector) = toGlobalPos(size.getPos(anchor))
    fun getAnchor(pos: Vector) = size.getAnchor(toRelativePos(pos))

    operator fun contains(pos: Vector) = toRelativePos(pos) in size
    fun intersects(b: Bounds) = ((center - b.center).abs()) in ((size + b.size)/2)

    //modifiers
    fun setPos(p: Vector, anchor: Vector) = globalMove(p - getPos(anchor))
    fun setCenter(p: Vector) = Bounds(p, size, angle)
    fun localMove(p: Vector) = setCenter(center + p.rotate(angle))
    fun globalMove(p: Vector) = setCenter(center + p)

    fun setSize(s: TwoDimensionalDouble, anchor: Vector)
            = Bounds(center, s.toArea(), angle).setPos(getPos(anchor), anchor)
    fun setSize(s: TwoDimensionalInt, anchor: Vector)
            = Bounds(center, s.toArea(), angle).setPos(getPos(anchor), anchor)
    fun setWidth(w: Double, anchor: Double) = setSize(Area(w, size.height), Vector(anchor, 0))
    fun setHeight(h: Double, anchor: Double) = setSize(Area(size.width, h), Vector(0, anchor))

    fun resize(s: TwoDimensionalDouble, anchor: Vector) = setSize(size + s, anchor)
    fun resize(s: TwoDimensionalInt, anchor: Vector) = setSize(size + s, anchor)
    fun resizeWidth(w: Double, anchor: Double) = setWidth(size.width + w, anchor)
    fun resizeHeight(h: Double, anchor: Double) = setHeight(size.height + h, anchor)

    fun scale(scale: Vector, anchor: Vector) = setSize(size*scale, anchor)
    fun scaleWidth(scale: Double, anchor: Double) = setWidth(size.width*scale, anchor)
    fun scaleHeight(scale: Double, anchor: Double) = setHeight(size.height*scale, anchor)

    fun fixRatio(ratio: Vector, anchor: Vector) = setSize((ratio * (size/ratio).min).toArea(), anchor)
    fun fixRatioElement(vElement: VElement, anchor: Vector = Vector.HALF)
            = fixRatio(vElement.size?.vector ?: Vector.ONE, anchor)

    fun rotate(a: Double) = Bounds(center, size, angle + a)
    fun setAngle(a: Double) = Bounds(center, size, a)
}
package velvet.structs

import velvet.velements.VElement

data class Bounds(val center: Vector = Vector(),
                  val size: Vector = Vector(),
                  val angle: Double = 0.0) {

    companion object{

        fun fromStartToEnd(start: Vector, end: Vector) = Bounds((start+end)/2, end-start)

        fun fromStartOfSize(start: Vector, size: Vector) = Bounds(start + size/2, size)
        fun fromCenterOfSize(center: Vector, size: Vector) = Bounds(center, size)
    }

    fun getPos(anchor: Vector) = center + ((anchor - 0.5)*size).rotate(angle)
    fun getAnchor(pos: Vector) = (pos - center).rotate(-angle) / size + 0.5

    fun contains(v: Vector) = (v - center).rotate(-angle).abs() < size/2

    //TODO: THESE ONLY WORKS FOR AXIS-ALIGNED BOUNDS PLEASE FIX!!
    fun overlaps(bounds: Bounds) = (center - bounds.center).abs() < (size + bounds.size)/2
    fun merge(bounds: Bounds) = fromStartToEnd(
            getPos(Vector(0)).min(bounds.getPos(Vector(0))),
            getPos(Vector(1)).max(bounds.getPos(Vector(1))))
    //TODO: END OF ONLY AA-BOUNDS SECTION

    fun move(v: Vector) = copy(center = center + v.rotate(angle))
    fun resize(v: Vector, anchor: Vector = Vector()): Bounds{
        val anchorPos = getPos(anchor)
        val resized = copy(size = v)
        val anchorOffset = anchorPos - resized.getPos(anchor)
        return resized.copy(center = resized.center + anchorOffset)
    }
    fun scale(scale: Vector, anchor: Vector = Vector(0.5)) = resize(size*scale, anchor)
    fun fixRatio(ratio: Vector, anchor: Vector = Vector(0.5)) = resize(ratio * (size/ratio).min, anchor)
    fun fixRatioElement(element: VElement?, anchor: Vector = Vector(0.5))
            = fixRatio(element?.size ?: Vector(1), anchor)
}
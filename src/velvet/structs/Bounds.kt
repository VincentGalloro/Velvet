package velvet.structs

data class Bounds(val start: Vector = Vector(),
                  val end: Vector = Vector()) {

    constructor(pos: Vector) : this(pos, pos)

    val size by lazy { end-start }

    operator fun plus(v: Vector) = Bounds(start+v, end+v)
    operator fun times(v: Vector) = Bounds(start*v, end*v)

    fun contains(v: Vector) = v.greaterThan(start) && v.lessThan(end)

    fun getPos(anchor: Vector) = start + size*anchor

    fun resize(size: Vector, anchor: Vector): Bounds{
        val p = getPos(anchor)
        return Bounds(p - size*anchor, p - size*anchor + size)
    }
    fun scale(scale: Vector, anchor: Vector) = resize(size*scale, anchor)
    fun fixRatio(ratio: Vector, anchor: Vector) = resize(ratio * (size/ratio).min, anchor)

    fun subBounds(range: Bounds) = range*size + start
}
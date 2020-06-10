package velvet.util.types

import velvet.util.types.spatial.Vector
import java.awt.Shape
import java.awt.geom.Line2D
import java.awt.geom.Rectangle2D

interface VShape {

    val javaShape: Shape
}

class VRect(val pos: Vector, val size: Vector) : VShape {

    override val javaShape by lazy { Rectangle2D.Double(pos.x, pos.y, size.x, size.y) }
}

class VLine(val start: Vector, val end: Vector): VShape {

    override val javaShape by lazy { Line2D.Double(start.x, start.y, end.x, end.y) }
}
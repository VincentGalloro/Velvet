package velvet.util.types

import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Vector
import java.awt.Shape
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.Rectangle2D
import java.awt.geom.RoundRectangle2D

interface VShape {

    val javaShape: Shape
}

class VRect(private val pos: Vector, private val size: Area) : VShape {

    companion object{
        fun fromBounds(bounds: Bounds) = VRect(bounds.getPos(Vector()), bounds.size)
    }

    override val javaShape by lazy { Rectangle2D.Double(pos.x, pos.y, size.width, size.height) }
}

class VRoundedRect(private val pos: Vector,
                   private val size: Area,
                   private val rounding: Double): VShape{

    companion object{
        fun fromBounds(bounds: Bounds, rounding: Double) 
                = VRoundedRect(bounds.getPos(Vector()), bounds.size, rounding)
    }

    override val javaShape by lazy {
        RoundRectangle2D.Double(pos.x, pos.y, size.width, size.height, rounding, rounding)
    }
}

class VCircle(private val pos: Vector, private val size: Area) : VShape {

    companion object{
        fun fromCenter(pos: Vector, size: Area) = VCircle(pos-size.vector/2, size)
    }

    override val javaShape by lazy { Ellipse2D.Double(pos.x, pos.y, size.width, size.height) }
}

class VLine(private val start: Vector, private val end: Vector): VShape {

    override val javaShape by lazy { Line2D.Double(start.x, start.y, end.x, end.y) }
}
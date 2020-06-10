package velvet.util.types.spatial

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class BoundsTest {

    @Test
    fun fromStartOfArea(){
        val b = Bounds.fromStartOfArea(Vector(100, 200), Area(20, 50))

        assertEquals(Vector(110, 225), b.center)
        assertEquals(Area(20, 50), b.area)
        assertEquals(0.0, b.angle, 0.0001)
    }

    @Test
    fun getPos() {
        val b = Bounds.fromStartOfArea(Vector(100, 200), Area(20, 50))

        assertEquals(Vector(100, 200), b.getPos(Vector()))
        assertEquals(Vector(115, 225), b.getPos(Vector(0.75, 0.5)))
        assertEquals(Vector(120, 250), b.getPos(Vector(1)))
        assertEquals(Vector(105, 250), b.getPos(Vector(0.25, 1.0)))
    }

    @Test
    fun getAnchor() {
        val b = Bounds.fromStartOfArea(Vector(100, 200), Area(20, 50))

        assertEquals(0.0, b.getAnchor(Vector(100, 200)).x, 0.0001)
        assertEquals(0.0, b.getAnchor(Vector(100, 200)).y, 0.0001)
        assertEquals(Vector(0.75, 0.5), b.getAnchor(Vector(115, 225)))
        assertEquals(Vector(1), b.getAnchor(Vector(120, 250)))
        assertEquals(0.25, b.getAnchor(Vector(105, 250)).x, 0.0001)
        assertEquals(1.0, b.getAnchor(Vector(105, 250)).y, 0.0001)
    }
}
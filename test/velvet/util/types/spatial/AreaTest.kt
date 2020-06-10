package velvet.util.types.spatial

import org.junit.Test

import org.junit.Assert.*

class AreaTest {

    @Test
    fun getPos() {
        val a = Area(20, 50)

        assertEquals(Vector(0, 0), a.getPos(Vector()))
        assertEquals(Vector(15, 25), a.getPos(Vector(0.75, 0.5)))
        assertEquals(Vector(20, 50), a.getPos(Vector(1)))
        assertEquals(Vector(5, 50), a.getPos(Vector(0.25, 1.0)))
    }

    @Test
    fun getAnchor() {
        val a = Area(20, 50)

        assertEquals(Vector(), a.getAnchor(Vector()))
        assertEquals(Vector(0.75, 0.5), a.getAnchor(Vector(15, 25)))
        assertEquals(Vector(1), a.getAnchor(Vector(20, 50)))
        assertEquals(Vector(0.25, 1.0), a.getAnchor(Vector(5, 50)))
    }
}
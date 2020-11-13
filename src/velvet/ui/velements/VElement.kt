package velvet.ui.velements

import velvet.main.VGraphics
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Vector
import velvet.util.types.spatial.Area

interface VElement {

    val size: Area?

    fun render(g: VGraphics, targetSize: Area)

    fun render(g: VGraphics, bounds: Bounds){
        g.save()

        g.translate(bounds.getPos(Vector()))
        g.rotate(bounds.angle)
        render(g, bounds.size)

        g.reset()
    }
}

package velvet.ui.vcontainer.velements

import velvet.main.VGraphics
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Vector
import velvet.ui.vcontainer.VContainer
import velvet.util.types.spatial.Area

interface VElement {

    val area: Area?

    fun createContainer() = VContainer(this)

    fun render(g: VGraphics, targetArea: Area)

    fun render(g: VGraphics, bounds: Bounds){
        g.save()

        g.translate(bounds.getPos(Vector()))
        g.rotate(bounds.angle)
        render(g, bounds.area)

        g.reset()
    }
}

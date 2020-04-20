package velvet.ui.vcontainer

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.structs.Vector
import velvet.ui.vcontainer.velements.VElement

class VContainer(var vElement: VElement? = null) {

    var bounds = Bounds()

    fun render(g: VGraphics){
        vElement?.let {
            g.save()

            g.translate(bounds.getPos(Vector()))
            g.rotate(bounds.angle)
            it.render(g, bounds.size)

            g.reset()
        }
    }
}
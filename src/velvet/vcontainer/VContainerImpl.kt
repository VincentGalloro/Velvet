package velvet.vcontainer

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.structs.Vector
import velvet.vcontainer.velement.VElement

class VContainerImpl(override var vElement: VElement? = null) : VContainer {

    override var bounds = Bounds()

    override fun render(g: VGraphics){
        vElement?.let {
            g.save()

            g.translate(bounds.getPos(Vector()))
            g.rotate(bounds.angle)
            it.render(g, bounds.size)

            g.reset()
        }
    }
}
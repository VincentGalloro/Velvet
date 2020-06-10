package velvet.ui.vcontainer

import velvet.main.VGraphics
import velvet.util.types.spatial.Bounds
import velvet.ui.vcontainer.velements.VElement

class VContainer(var vElement: VElement? = null) {

    var bounds = Bounds()

    fun render(g: VGraphics){
        vElement?.render(g, bounds)
    }
}
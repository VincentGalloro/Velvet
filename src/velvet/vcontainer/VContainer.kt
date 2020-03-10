package velvet.vcontainer

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.vcontainer.velement.VElement

interface VContainer {

    var vElement: VElement?
    var bounds: Bounds

    fun render(g: VGraphics)
}
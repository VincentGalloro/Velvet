package velvet.velements

import velvet.main.VGraphics
import velvet.structs.Vector

interface VElement {

    val size: Vector?

    fun render(g: VGraphics, targetSize: Vector)
}

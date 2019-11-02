package velvet.velements

import velvet.main.VGraphics
import velvet.structs.Vector

interface VDecorator {

    fun render(g: VGraphics, size: Vector)
}
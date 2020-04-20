package velvet.ui.vcontainer.velements

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.ui.vcontainer.VContainer

interface VElement {

    val size: Vector?

    fun createContainer() = VContainer(this)

    fun render(g: VGraphics, targetSize: Vector)
}

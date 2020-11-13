package velvet.ui.components.graphical

import velvet.main.VGraphics
import velvet.util.types.spatial.Vector
import velvet.ui.UINode
import velvet.ui.components.BasicComponent
import java.awt.image.BufferedImage

class WindowedComponent : BasicComponent() {

    private var buffer: BufferedImage? = null

    override fun preRender(uiNode: UINode, g: VGraphics) {
        val size = uiNode.bounds.size.toSize()

        val bufferedImage = BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB)
        buffer = bufferedImage

        val imgG = VGraphics(bufferedImage.createGraphics())
        imgG.translate(-uiNode.bounds.getPos(Vector()))
        g.subGraphics(imgG.baseGraphics)
    }

    override fun postRender(uiNode: UINode, g: VGraphics) {
        buffer?.let {
            g.resetGraphics()
            g.drawImage(it, uiNode.bounds.getPos(Vector()))
        }
    }
}
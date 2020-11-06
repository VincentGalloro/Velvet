package velvet.ui.components

import velvet.main.VGraphics
import velvet.util.types.spatial.Vector
import velvet.ui.UINode
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

class WindowedComponent : BasicComponent() {

    private var buffer: BufferedImage? = null

    override fun preRender(uiNode: UINode, g: VGraphics) {
        val bufferedImage = BufferedImage(uiNode.bounds.area.width.roundToInt(),
                uiNode.bounds.area.height.roundToInt(),
                BufferedImage.TYPE_INT_ARGB)
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
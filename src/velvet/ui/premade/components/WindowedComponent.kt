package velvet.ui.premade.components

import velvet.main.VGraphics
import velvet.structs.Vector
import velvet.ui.UIEventListener
import velvet.ui.UINode
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

class WindowedComponent : BasicComponent() {

    private var buffer: BufferedImage? = null

    override fun preRender(uiNode: UINode, g: VGraphics) {
        if (uiNode.bounds.size.x.roundToInt() <= 0 || uiNode.bounds.size.y.roundToInt() <= 0) {
            buffer = null
            return
        }

        val bufferedImage = BufferedImage(uiNode.bounds.size.x.roundToInt(),
                uiNode.bounds.size.y.roundToInt(),
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
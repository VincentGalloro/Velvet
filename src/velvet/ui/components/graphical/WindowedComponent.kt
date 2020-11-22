package velvet.ui.components.graphical

import velvet.game.graphics.Sprite
import velvet.main.VGraphics
import velvet.util.types.spatial.Vector
import velvet.ui.UINode
import velvet.ui.components.BasicComponent
import velvet.util.types.spatial.Size
import java.awt.image.BufferedImage

class WindowedComponent : BasicComponent() {

    private var buffer: Sprite = Sprite.emptySprite(Size())

    override fun preRender(uiNode: UINode, g: VGraphics) {
        val size = uiNode.bounds.size.toSize()
        buffer = Sprite.emptySprite(size)
        val imgG = buffer.createGraphics()
        imgG.translate(-uiNode.bounds.getPos(Vector()))
        g.subGraphics(imgG.baseGraphics)
    }

    override fun postRender(uiNode: UINode, g: VGraphics) {
        g.resetGraphics()
        g.draw(buffer, uiNode.bounds.getPos(Vector()))
    }
}
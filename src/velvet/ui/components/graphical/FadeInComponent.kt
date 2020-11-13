package velvet.ui.components.graphical

import velvet.main.VGraphics
import velvet.ui.UINode
import velvet.ui.components.BasicComponent
import java.awt.AlphaComposite

class FadeInComponent(private val speed: Double = 0.04) : BasicComponent() {

    private var alpha = 0.0

    override fun preUpdate(uiNode: UINode) {
        alpha += speed
        if(alpha >= 1.0) enabled = false
    }

    override fun preRender(uiNode: UINode, g: VGraphics) {
        g.baseGraphics.composite = AlphaComposite.SrcOver.derive(alpha.toFloat())
    }

    override fun postRender(uiNode: UINode, g: VGraphics) {
        g.baseGraphics.composite = AlphaComposite.SrcOver
    }
}
package velvet.ui.components.graphical

import velvet.main.VGraphics
import velvet.ui.UINode
import velvet.ui.components.BasicComponent
import java.awt.AlphaComposite

class AlphaComponent : BasicComponent() {

    var alpha = 1.0
        set(value){
            field = when{
                value < 0.0 -> 0.0
                value > 1.0 -> 1.0
                else -> value
            }
        }

    override fun preRender(uiNode: UINode, g: VGraphics) {
        g.baseGraphics.composite = AlphaComposite.SrcOver.derive(alpha.toFloat())
    }

    override fun postRender(uiNode: UINode, g: VGraphics) {
        g.baseGraphics.composite = AlphaComposite.SrcOver
    }
}
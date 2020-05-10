package velvet.ui.premade.components

import velvet.main.VGraphics
import velvet.ui.UIEventListener
import velvet.ui.UINode

interface UIComponent {

    var enabled: Boolean

    val uiEventListener: UIEventListener

    fun preUpdate(uiNode: UINode)
    fun postUpdate(uiNode: UINode)

    fun preRender(uiNode: UINode, g: VGraphics)
    fun postRender(uiNode: UINode, g: VGraphics)
}

open class BasicComponent : UIComponent{

    final override var enabled = true
    final override val uiEventListener = UIEventListener()

    override fun preUpdate(uiNode: UINode) {}
    override fun postUpdate(uiNode: UINode) {}

    override fun preRender(uiNode: UINode, g: VGraphics) {}
    override fun postRender(uiNode: UINode, g: VGraphics) {}
}
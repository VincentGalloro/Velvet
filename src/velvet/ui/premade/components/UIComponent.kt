package velvet.ui.premade.components

import velvet.main.VGraphics
import velvet.ui.UIEventListener
import velvet.ui.UINode

interface UIComponent {

    val uiEventListener: UIEventListener

    fun preUpdate(uiNode: UINode){}
    fun postUpdate(uiNode: UINode){}

    fun preRender(uiNode: UINode, g: VGraphics){}
    fun postRender(uiNode: UINode, g: VGraphics){}
}
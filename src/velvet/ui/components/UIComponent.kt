package velvet.ui.components

import velvet.io.hardware.InputEventListener
import velvet.main.VGraphics
import velvet.ui.events.UIEventListener
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

    override var enabled = true

    override val uiEventListener = UIEventListener()

    override fun preUpdate(uiNode: UINode) {}
    override fun postUpdate(uiNode: UINode) {}

    override fun preRender(uiNode: UINode, g: VGraphics) {}
    override fun postRender(uiNode: UINode, g: VGraphics) {}
}

class BasicEventComponent(eventSetter: UIEventListener.()->Unit) : BasicComponent() {
    init{ eventSetter(uiEventListener) }
}
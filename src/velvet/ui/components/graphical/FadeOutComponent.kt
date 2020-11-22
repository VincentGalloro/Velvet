package velvet.ui.components.graphical

import velvet.main.VGraphics
import velvet.ui.UINode
import velvet.ui.components.BasicComponent

class FadeOutComponent(private val speed: Double = 0.04) : BasicComponent() {

    private val alphaComponent = AlphaComponent()

    val isDone get() = alphaComponent.alpha >= 1.0

    init {
        alphaComponent.alpha = 1.0
    }

    override fun preUpdate(uiNode: UINode) {
        alphaComponent.alpha += speed
        if(isDone) enabled = false
    }

    override fun preRender(uiNode: UINode, g: VGraphics) = alphaComponent.preRender(uiNode, g)

    override fun postRender(uiNode: UINode, g: VGraphics) = alphaComponent.postRender(uiNode, g)
}
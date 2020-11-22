package velvet.ui.components.graphical

import velvet.main.VGraphics
import velvet.ui.UINode
import velvet.ui.components.BasicComponent

class DecayComponent(private val initialTime: Int,
                     private val fadeTime: Int) : BasicComponent(){

    private val alphaComponent = AlphaComponent()

    var hasDecayed = false
        private set(value){
            if(value && !field){ //can only transition once from false->true
                field = value
                onDecay?.invoke()
                enabled = false
            }
        }

    var onDecay: (()->Unit)? = null

    var timeLeft: Int = initialTime
        set(value){
            if(!hasDecayed) {
                field = value
                alphaComponent.alpha = value / fadeTime.toDouble()
                if (value <= 0) hasDecayed = true
            }
        }

    fun refresh(){
        if(!hasDecayed) {
            timeLeft = initialTime
        }
    }

    override fun preUpdate(uiNode: UINode) {
        timeLeft--
    }

    override fun preRender(uiNode: UINode, g: VGraphics) = alphaComponent.preRender(uiNode, g)
    override fun postRender(uiNode: UINode, g: VGraphics) = alphaComponent.postRender(uiNode, g)
}
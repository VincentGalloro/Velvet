package velvet.velements.container

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.velements.VElement
import velvet.velements.interact.UIEventListener

open class VContainer(var vElement: VElement? = null) {

    var bounds: Bounds = Bounds()
    var uiEventListener = UIEventListener()

    var disabled: Boolean = false

    fun render(g: VGraphics){
        if(disabled){ return; }
        vElement?.let {
            g.save()

            g.translate(bounds.start)
            it.render(g, bounds.size)

            g.reset()
        }
    }
}
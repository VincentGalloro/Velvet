package velvet.velements.container

import velvet.main.VGraphics
import velvet.structs.Bounds
import velvet.structs.Vector
import velvet.velements.VElement
import velvet.velements.interact.UIEventListener

open class VContainer (var vElement: VElement? = null,
                       var boundsContainer: BoundsContainer = BoundsContainer.of(Bounds())){

    var bounds: Bounds
        get() = boundsContainer.bounds
        set(value){ boundsContainer = BoundsContainer.of(value) }

    var uiEventListener = UIEventListener()
    val subContainers: MutableSet<VContainer> = mutableSetOf()
    var disabled: Boolean = false

    fun trackTo(target: Bounds){
        boundsContainer = BoundsContainer.trackingFromTo(bounds, target)
    }
    fun track(boundsGenerator: (VElement?) -> Bounds){
        boundsContainer = BoundsContainer.tracking { boundsGenerator(it) }
    }

    fun update(){
        if(disabled){ return; }

        boundsContainer.update(vElement)
        subContainers.forEach { it.update() }
    }

    fun render(g: VGraphics){
        if(disabled){ return; }
        vElement?.let {
            g.save()

            g.translate(bounds.getPos(Vector()))
            g.rotate(bounds.angle)
            it.render(g, bounds.size)

            g.reset()
        }

        subContainers.forEach { it.render(g) }
    }
}
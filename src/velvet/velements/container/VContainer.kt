package velvet.velements.container

import velvet.main.VGraphics
import velvet.smooth.actuators.Actuator
import velvet.smooth.actuators.impl.BoundsSwishActuator
import velvet.structs.Bounds
import velvet.structs.Vector
import velvet.velements.VElement
import velvet.velements.interact.UIEventListener

open class VContainer(var vElement: VElement? = null,
                      var boundsGenerator: ((VContainer) -> Bounds)? = null) {

    var bounds: Bounds = Bounds()
    var uiEventListener = UIEventListener()

    val subContainers: MutableSet<VContainer> = HashSet()

    var disabled: Boolean = false

    fun trackTo(target: Bounds, actuator: Actuator<Bounds> = BoundsSwishActuator()){
        boundsGenerator = { actuator.step(it.bounds, target) }
    }

    fun update(){
        if(disabled){ return; }
        boundsGenerator?.invoke(this)?.also { bounds = it }

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
package velvet.vcontainer.interact

import velvet.structs.Bounds
import velvet.vcontainer.VContainer
import velvet.vcontainer.VContainerImpl
import velvet.vcontainer.velement.VElement

class TrackedVContainer(vElement: VElement,
                        var elementFixedRatio: Boolean = false,
                        var boundsGenerator: ((VElement?)->Bounds)) :
        VContainer by VContainerImpl(vElement){

    fun update(){
        bounds = boundsGenerator(vElement)
        if(elementFixedRatio) bounds = bounds.fixRatioElement(vElement)
    }
}
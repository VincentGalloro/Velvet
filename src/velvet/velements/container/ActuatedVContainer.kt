package velvet.velements.container

import velvet.structs.Bounds
import velvet.velements.VElement

class ActuatedVContainer(vElement: VElement? = null) : VContainer(vElement){

    val layoutActuator = BoundsActuator(this)

    fun fullSetBounds(bounds: Bounds){
        this.bounds = bounds
        layoutActuator.targetBounds = bounds
    }

    fun update(){
        if(disabled){ return; }
        layoutActuator.step()
    }
}

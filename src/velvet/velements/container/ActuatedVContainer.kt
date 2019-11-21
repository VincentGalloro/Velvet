package velvet.velements.container

import velvet.velements.VElement

class ActuatedVContainer(vElement: VElement? = null) : VContainer(vElement){

    val layoutActuator = LayoutActuator(this)

    fun update(){
        layoutActuator.step()
    }
}

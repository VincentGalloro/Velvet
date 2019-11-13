package velvet.velements.container

import velvet.velements.VElement

class ActuatedVContainer(vUnit: VElement? = null) : VContainer(vUnit){

    val layoutActuator = LayoutActuator(this)

    fun update(){
        layoutActuator.step()
    }
}

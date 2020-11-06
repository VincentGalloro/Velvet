package velvet.main

import velvet.util.types.spatial.Size

class TestVelvet(velvetState: VelvetState) : Velvet(velvetState) {

    override fun update() {
        if(inputEventStream.events.isNotEmpty()) {
            println(inputEventStream.state)
            var state = inputEventStream.state
            inputEventStream.events.forEach {
                println(it)
                state = it.updateInputState(state)
                println(state)
            }
        }
    }

    override fun render(g: VGraphics) {
    }

    override fun onClose() {
    }
}

fun main(){
    Velvet.start({ TestVelvet(it) }, Size(1500, 832), "Test Velvet")
}
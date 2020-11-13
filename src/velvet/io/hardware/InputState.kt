package velvet.io.hardware

import velvet.util.types.spatial.Vector

data class InputState(val mousePos: Vector,
                      val mouseButtonsDown: Set<Int>,
                      val keysDown: Set<Int>){

    companion object{
        fun new() = InputState(Vector(), emptySet(), emptySet())
    }
}
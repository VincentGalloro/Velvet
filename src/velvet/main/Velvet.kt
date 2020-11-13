package velvet.main

import velvet.io.hardware.InputEventStream
import velvet.ui.UIEventHandler
import velvet.ui.UINode
import velvet.util.types.spatial.Size

class VelvetState(val size: Size,
                  val inputEventStream: InputEventStream,
                  val uiEventHandler: UIEventHandler,
                  val rootNode: UINode,
                  val fileDrop: FileDrop)

abstract class Velvet(private val velvetState: VelvetState){

    val size: Size get() = velvetState.size
    val inputEventStream: InputEventStream get() = velvetState.inputEventStream
    val uiEventHandler: UIEventHandler get() = velvetState.uiEventHandler
    val rootNode: UINode get() = velvetState.rootNode
    val fileDrop: FileDrop get() = velvetState.fileDrop

    abstract fun onClose()

    companion object {
        fun start(levelGenerator: (VelvetState)->Velvet, size: Size, name: String) {
            Main(levelGenerator, size, name)
        }
    }
}
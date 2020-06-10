package velvet.main

import velvet.ui.UIEventHandler
import velvet.util.types.spatial.Size

class VelvetState(val size: Size,
                  val mouse: Mouse,
                  val keyboard: Keyboard,
                  val uiEventHandler: UIEventHandler,
                  val fileDrop: FileDrop)

abstract class Velvet(private val velvetState: VelvetState){

    val size: Size get() = velvetState.size
    val mouse: Mouse get() = velvetState.mouse
    val keyboard: Keyboard get() = velvetState.keyboard
    val uiEventHandler: UIEventHandler get() = velvetState.uiEventHandler
    val fileDrop: FileDrop get() = velvetState.fileDrop

    abstract fun init()
    abstract fun update()
    abstract fun render(g: VGraphics)
    abstract fun onClose()

    companion object {
        fun start(levelGenerator: (VelvetState)->Velvet, size: Size, name: String) {
            Main(levelGenerator, size, name)
        }
    }
}
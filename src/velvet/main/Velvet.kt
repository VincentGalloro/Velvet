package velvet.main

import velvet.structs.Position
import velvet.vcontainer.interact.UIEventHandler

abstract class Velvet(var size: Position) {

    protected lateinit var mouse: Mouse
    protected lateinit var keyboard: Keyboard
    protected lateinit var uiEventHandler: UIEventHandler
    protected lateinit var fileDrop: FileDrop

    abstract fun init()
    abstract fun update()
    abstract fun render(g: VGraphics)
    abstract fun onClose()

    companion object {
        fun start(level: Velvet, name: String) {
            Main(level, name)
        }
    }
}
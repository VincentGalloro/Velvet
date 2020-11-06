package velvet.game

import velvet.io.hardware.Mouse
import velvet.main.VGraphics
import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Size
import velvet.util.types.spatial.Vector

class Camera(private val mouse: Mouse, private val windowSize: Size) {

    var bounds = Bounds.fromStartOfArea(Vector(), windowSize.area)


    fun update(){
    }

    fun render(g: VGraphics){
        g.translate(-bounds.getPos(Vector()))
        g.scale(windowSize.vector / bounds.area.vector)
    }
}
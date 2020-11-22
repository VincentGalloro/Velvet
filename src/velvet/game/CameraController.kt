package velvet.game

import velvet.ui.components.BasicComponent
import velvet.util.types.spatial.Vector
import java.awt.event.MouseEvent
import kotlin.math.pow

class CameraController(private val camera: Camera) : BasicComponent() {

    private var worldAnchor: Vector = Vector()

    init{
        uiEventListener.onMouseButtonPressed = { event, state ->
            if(event.button == MouseEvent.BUTTON2) {
                worldAnchor = camera.fromScreenPos(state.mousePos)
            }
        }
        uiEventListener.onMouseMoved = { event, state ->
            if(MouseEvent.BUTTON2 in state.mouseButtonsDown){
                camera.targetBounds = (camera.targetBounds ?: camera.bounds)
                        .setPos(worldAnchor, camera.windowBounds.getAnchor(event.pos))
            }
        }
        uiEventListener.onMouseWheelScrolled = { event, state ->
            if(MouseEvent.BUTTON2 !in state.mouseButtonsDown){
                worldAnchor = camera.fromScreenPos(state.mousePos)
            }
            camera.targetBounds = (camera.targetBounds ?: camera.bounds)
                    .scale(Vector(0.9.pow(-event.amount)), Vector())
                    .setPos(worldAnchor, camera.windowBounds.getAnchor(state.mousePos))
        }
    }
}
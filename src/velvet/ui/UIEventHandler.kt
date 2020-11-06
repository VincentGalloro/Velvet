package velvet.ui

import velvet.io.hardware.InputEventListener
import velvet.io.hardware.InputEventListenerImpl
import velvet.io.hardware.InputEventStream
import velvet.ui.events.*
import velvet.util.types.spatial.Vector
import java.awt.event.MouseEvent

class UIEventHandler(private val inputEventStream: InputEventStream) {

    var root: UINode? = null

    var targetChain: List<UINode> = emptyList()

    var hoverChain: List<UINode> = emptyList()
    var focusChain: List<UINode> = emptyList()

    var eventRedirect: InputEventListener = InputEventListenerImpl()

    init{
        eventRedirect.onMouseButtonPressed = { e, s ->
            if(e.button == MouseEvent.BUTTON1) switchFocus()
            handleEvent(hoverChain){ it.handleInputEvent(e, s) }
        }
        eventRedirect.onMouseButtonReleased = { e, s ->
            if(e.button == MouseEvent.BUTTON1) switchHover()
            handleEvent(hoverChain){ it.handleInputEvent(e, s) }
        }
        eventRedirect.onMouseWheelScrolled = { e, s ->
            handleEvent(hoverChain){ it.handleInputEvent(e, s) }
        }
        eventRedirect.onMouseMoved = { e, s ->
            updateTarget(e.pos)
            if(MouseEvent.BUTTON1 !in s.mouseButtonsDown) switchHover()
            handleEvent(hoverChain){ it.handleInputEvent(e, s) }
        }
        eventRedirect.onKeyPressed = { e, s -> handleEvent(focusChain){ it.handleInputEvent(e, s) } }
        eventRedirect.onKeyReleased = { e, s -> handleEvent(focusChain){ it.handleInputEvent(e, s) } }
        eventRedirect.onCharTyped = { e, s -> handleEvent(focusChain){ it.handleInputEvent(e, s) } }
    }

    private fun handleEvent(chain: List<UINode>, eventRunner: (UIEventListener)->Unit?){
        chain.forEach { uiNode ->
            if(uiNode.activeComponents.map { eventRunner(it.uiEventListener) }.any { it != null }) return
        }
    }

    private fun createChain(chain: List<UINode>, eventChecker: (UIEventListener) -> Boolean): List<UINode> {
        return chain.dropWhile { uiNode -> uiNode.activeComponents.none { eventChecker(it.uiEventListener) } }
    }

    private fun switchHover(){
        handleEvent(listOfNotNull(hoverChain.firstOrNull())){ it.handleUiEvent(HoverEndEvent()) }
        hoverChain = createChain(targetChain, UIEventListener::isHoverable)
        handleEvent(listOfNotNull(hoverChain.firstOrNull())){ it.handleUiEvent(HoverStartEvent()) }
    }

    private fun switchFocus(){
        handleEvent(listOfNotNull(focusChain.firstOrNull())){ it.handleUiEvent(FocusEndEvent()) }
        focusChain = createChain(targetChain, UIEventListener::isFocusable)
        handleEvent(listOfNotNull(focusChain.firstOrNull())){ it.handleUiEvent(FocusStartEvent()) }
    }

    private fun updateTarget(mousePos: Vector){
        targetChain = generateSequence(root?.takeIf { it.isHovered(mousePos) }) {
            it.findHoveredSubNode(mousePos)
        }.toList().reversed()
    }

    fun update(){
        updateTarget(inputEventStream.state.mousePos)
        if(MouseEvent.BUTTON1 !in inputEventStream.state.mouseButtonsDown) switchHover()
        inputEventStream.feedToListener(eventRedirect)
    }
}
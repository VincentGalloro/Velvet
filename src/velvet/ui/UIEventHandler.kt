package velvet.ui

import velvet.main.Keyboard
import velvet.main.Mouse

class UIEventHandler(private val mouse: Mouse,
                     private val keyboard: Keyboard) {

    var root: UINode? = null

    var targetChain: List<UINode> = emptyList()

    var hoverChain: List<UINode> = emptyList()
    var focusChain: List<UINode> = emptyList()

    private fun handleEvent(chain: List<UINode>, eventRunner: (UIEventListener)->Unit?){
        chain.forEach { uiNode ->
            if(uiNode.activeComponents.map { eventRunner(it.uiEventListener) }.any { it != null }) return
        }
    }

    private fun createChain(chain: List<UINode>, eventChecker: (UIEventListener) -> Boolean): List<UINode> {
        return chain.dropWhile { uiNode -> uiNode.activeComponents.none { eventChecker(it.uiEventListener) } }
    }

    private fun switchHover(){
        handleEvent(listOfNotNull(hoverChain.firstOrNull())){ it.onHoverEnd?.invoke() }
        hoverChain = createChain(targetChain, UIEventListener::isHoverable)
        handleEvent(listOfNotNull(hoverChain.firstOrNull())){ it.onHoverStart?.invoke() }
    }

    private fun switchFocus(){
        handleEvent(listOfNotNull(focusChain.firstOrNull())){ it.onFocusEnd?.invoke() }
        focusChain = createChain(targetChain, UIEventListener::isFocusable)
        handleEvent(listOfNotNull(focusChain.firstOrNull())){ it.onFocusStart?.invoke() }
    }

    private fun updateTarget(){
        targetChain = generateSequence(
                root?.takeIf { it.isHovered(mouse.pos) },
                { it.subNodes.asReversed().find { uiContainer -> uiContainer.isHovered(mouse.pos) } }
        ).toList().reversed()
    }

    fun update(){
        updateTarget()

        if(mouse.isPressed(Mouse.LEFT)){
            switchFocus()
            handleEvent(targetChain) { it.onMousePress?.invoke(mouse.pos) }
        }
        if(mouse.isPressed(Mouse.RIGHT)){
            switchFocus()
            handleEvent(targetChain) { it.onRightClick?.invoke(mouse.pos) }
        }
        if(mouse.isPressed(Mouse.MIDDLE)){
            switchFocus()
            handleEvent(targetChain) { it.onMiddleClick?.invoke(mouse.pos) }
        }
        if(mouse.scrollAmount != 0){
            handleEvent(targetChain) { it.onMouseScroll?.invoke(mouse.scrollAmount) }
        }

        if(mouse.isReleased(Mouse.LEFT)){
            handleEvent(targetChain) { it.onMouseRelease?.invoke(mouse.pos) }
        }

        if(mouse.isDown(Mouse.LEFT)) {
            handleEvent(hoverChain) { it.onMouseDrag?.invoke(mouse.pos) }
        }
        else{
            if(createChain(targetChain, UIEventListener::isHoverable).firstOrNull() != hoverChain.firstOrNull()) {
                switchHover()
            }
            handleEvent(hoverChain) { it.onMouseHover?.invoke(mouse.pos) }
        }

        keyboard.pressedLog.forEach { code -> handleEvent(focusChain) { it.onKeyPressed?.invoke(code) } }
        keyboard.heldLog.forEach { code -> handleEvent(focusChain) { it.onKeyHeld?.invoke(code) } }
        keyboard.releasedLog.forEach { code -> handleEvent(focusChain) { it.onKeyReleased?.invoke(code) } }
        keyboard.textTyped.forEach { char -> handleEvent(focusChain) { it.onCharTyped?.invoke(char) } }
    }
}
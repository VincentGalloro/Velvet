package velvet.velements.interact

import velvet.structs.Vector

class UIEventListener {

    var onMousePress: (()->Unit)? = null
    var onMouseRelease: (()->Unit)? = null

    var onRightClick: (()->Unit)? = null
    var onMiddleClick: (()->Unit)? = null

    var onMouseHover: ((Vector)->Unit)? = null
    var onMouseDrag: ((Vector)->Unit)? = null

    var onHoverStart: (()->Unit)? = null
    var onHoverEnd: (()->Unit)? = null

    var onFocusStart: (()->Unit)? = null
    var onFocusEnd: (()->Unit)? = null

    var onKeyPressed: ((Int)->Unit)? = null
    var onKeyReleased: ((Int)->Unit)? = null
    var onCharTyped: ((Char)->Unit)? = null


    val isMouseInteractable: Boolean
        get() = onMousePress != null || onMouseRelease != null ||
                onRightClick != null || onMiddleClick != null ||
                onMouseHover != null || onMouseDrag != null

    val isKeyboardInteractable: Boolean
        get() = onKeyPressed != null || onKeyReleased != null || onCharTyped != null

    val isFocusable: Boolean
        get() = onFocusStart != null || onFocusEnd != null ||
                isMouseInteractable || isKeyboardInteractable

    val isHoverable: Boolean
        get() = onHoverStart != null || onHoverEnd != null || isFocusable
}
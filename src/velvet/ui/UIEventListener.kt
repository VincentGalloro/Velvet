package velvet.ui

import velvet.util.types.spatial.Vector

class UIEventListener {

    var onMousePress: ((Vector)->Unit)? = null
    var onMouseRelease: ((Vector)->Unit)? = null
    var onMouseScroll: ((Int)->Unit)? = null
    var onMouseDrag: ((Vector)->Unit)? = null

    var onRightClick: ((Vector)->Unit)? = null
    var onMiddleClick: ((Vector)->Unit)? = null

    var onHoverStart: (()->Unit)? = null
    var onHoverEnd: (()->Unit)? = null
    var onMouseHover: ((Vector)->Unit)? = null

    var onFocusStart: (()->Unit)? = null
    var onFocusEnd: (()->Unit)? = null

    var onKeyPressed: ((Int)->Unit)? = null
    var onKeyHeld: ((Int)->Unit)? = null
    var onKeyReleased: ((Int)->Unit)? = null
    var onCharTyped: ((Char)->Unit)? = null


    fun isHoverable() = listOf(onHoverStart, onHoverEnd, onMouseHover, onMouseDrag).any { it != null }
    fun isFocusable() = listOf(onFocusStart, onFocusEnd, onKeyPressed, onKeyHeld,
            onKeyReleased, onCharTyped).any { it != null }
}
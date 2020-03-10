package velvet.vcontainer.interact

import velvet.structs.Vector

class UIEventListener {

    var onMousePress: ((Vector)->Unit)? = null
    var onMouseRelease: ((Vector)->Unit)? = null
    var onMouseScroll: ((Int)->Unit)? = null

    var onRightClick: ((Vector)->Unit)? = null
    var onMiddleClick: ((Vector)->Unit)? = null

    var onHoverStart: (()->Unit)? = null
    var onHoverEnd: (()->Unit)? = null

    var onFocusStart: (()->Unit)? = null
    var onFocusEnd: (()->Unit)? = null

    var onKeyPressed: ((Int)->Unit)? = null
    var onKeyReleased: ((Int)->Unit)? = null
    var onCharTyped: ((Char)->Unit)? = null
}
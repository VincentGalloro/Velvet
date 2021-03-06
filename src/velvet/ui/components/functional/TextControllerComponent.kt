package velvet.ui.components.functional

import velvet.main.VGraphics
import velvet.util.StopWatch
import velvet.util.types.VColor
import velvet.util.types.spatial.Vector
import velvet.ui.UINode
import velvet.ui.components.BasicComponent
import velvet.ui.velements.TextElement
import velvet.util.types.VRect
import velvet.util.types.spatial.Area
import java.awt.geom.Rectangle2D

class TextControllerComponent(val textNode: UINode,
                              val textElement: TextElement) : BasicComponent(){

    private val textElementEditor = TextElementEditor(textElement)
    var onTextChange: ((String)->Unit)?
        get() = textElementEditor.onTextChange
        set(value) { textElementEditor.onTextChange = value }

    private var focused: Boolean = false
    private var cursorVisible: Boolean = false
        set(value){
            field = value
            cursorStopwatch.reset()
        }
    private var cursorStopwatch = StopWatch(30) { cursorVisible = !cursorVisible }

    init {
        uiEventListener.onFocusStart = {
            focused = true
            cursorVisible = true
        }
        uiEventListener.onFocusEnd = {
            focused = false
        }

        uiEventListener.onCharTyped = { it, _ ->
            textElementEditor.onCharTyped(it.char)
            cursorVisible = true
        }
        uiEventListener.onKeyPressed = { it, _ ->
            textElementEditor.onKeyPressed(it.code)
            cursorVisible = true
        }
    }

    override fun postUpdate(uiNode: UINode) {
        cursorStopwatch.update()
    }

    override fun postRender(uiNode: UINode, g: VGraphics) {
        if(!focused || !cursorVisible){ return }
        if(textElement !in textNode.vElements){
            System.err.println("Text Controller UINode binding does not contain text element")
            return
        }

        val charPos = textElement.textLayout.getCharPos(textElement.text,
                textElement.getFontMetrics(),
                textElementEditor.cursorIndex)

        g.save()
        g.translate(textNode.bounds.getPos(Vector()))
        g.rotate(textNode.bounds.angle)
        g.scale((textNode.bounds.size / textElement.size).vector)
        g.color = VColor.BLACK
        g.fill(VRect(charPos - Vector(0, textElement.font.size), Area(3, textElement.font.size)))
        g.reset()
    }
}
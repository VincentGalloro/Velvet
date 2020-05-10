package velvet.ui.premade.components

import velvet.main.VGraphics
import velvet.structs.StopWatch
import velvet.structs.VColor
import velvet.structs.Vector
import velvet.ui.vcontainer.VContainer
import velvet.ui.UIEventListener
import velvet.ui.UINode
import velvet.ui.vcontainer.velements.TextElement
import java.awt.geom.Rectangle2D

class TextControllerComponent(val vContainer: VContainer,
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
    private var cursorStopwatch = StopWatch(30){ cursorVisible = !cursorVisible }

    init {
        uiEventListener.onFocusStart = {
            focused = true
            cursorVisible = true
        }
        uiEventListener.onFocusEnd = {
            focused = false
        }

        uiEventListener.onCharTyped = {
            textElementEditor.onCharTyped(it)
            cursorVisible = true
        }
        uiEventListener.onKeyHeld = {
            textElementEditor.onKeyHeld(it)
            cursorVisible = true
        }
    }

    override fun postUpdate(uiNode: UINode) {
        cursorStopwatch.update()
    }

    override fun postRender(uiNode: UINode, g: VGraphics) {
        if(!focused || !cursorVisible){ return }

        val charPos = textElement.textLayout.getCharPos(textElement.text,
                textElement.getFontMetrics(),
                textElementEditor.cursorIndex)

        g.save()
        g.translate(vContainer.bounds.getPos(Vector()))
        g.rotate(vContainer.bounds.angle)
        g.scale(vContainer.bounds.size / textElement.size)
        g.color = VColor.BLACK
        g.fill(Rectangle2D.Double(charPos.x, charPos.y - textElement.font.size.toDouble(), 3.0, textElement.font.size.toDouble()))
        g.reset()
    }
}
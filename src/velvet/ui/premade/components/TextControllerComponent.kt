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
                              val textElement: TextElement) : UIComponent{

    private val textElementEditor = TextElementEditor(textElement)
    override val uiEventListener = UIEventListener()

    var canEdit: Boolean = true

    private var focused: Boolean = false
    private var cursorVisible: Boolean = false
        set(value){
            field = value
            cursorStopwatch.reset()
        }
    private var cursorStopwatch = StopWatch(30){ cursorVisible = !cursorVisible }

    init{
        uiEventListener.onFocusStart = {
            focused = true
            cursorVisible = true
        }
        uiEventListener.onFocusEnd = {
            focused = false
        }

        uiEventListener.onCharTyped = {
            if(canEdit){
                textElementEditor.onCharTyped(it)
                cursorVisible = true
            }
        }
        uiEventListener.onKeyHeld = {
            if(canEdit){
                textElementEditor.onKeyHeld(it)
                cursorVisible = true
            }
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
        g.setColor(VColor.BLACK)
        g.fill(Rectangle2D.Double(charPos.x, charPos.y - textElement.font.size.toDouble(), 3.0, textElement.font.size.toDouble()))
        g.reset()
    }
}
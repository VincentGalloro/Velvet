package velvet.ui.premade.components

import velvet.ui.vcontainer.velements.TextElement
import java.awt.event.KeyEvent

class TextElementEditor(private val textElement: TextElement) {

    var cursorIndex = textElement.text.length
        set(value){
            field = value
            onCursorMove?.invoke(value)
        }

    var onCursorMove: ((Int)->Unit)? = null
    var onTextChange: ((String)->Unit)? = null

    fun moveCursorToEnd(){
        cursorIndex = textElement.text.length
    }

    fun onCharTyped(c: Char) {
        var text = textElement.text

        if (c == '\b') { //backspace
            if (cursorIndex > 0) {
                text = text.substring(0, cursorIndex - 1) + text.substring(cursorIndex)
                cursorIndex--
            }
        } else if (c.toInt() == 0x7F) { //delete
            if (cursorIndex < text.length) {
                text = text.substring(0, cursorIndex) + text.substring(cursorIndex + 1)
            }
        } else if (c == '\n') { //enter
            if (false) { //TODO: change false to supports new line
                text = text.substring(0, cursorIndex) + c + text.substring(cursorIndex)
                cursorIndex++
            }
        } else {
            text = text.substring(0, cursorIndex) + c + text.substring(cursorIndex)
            cursorIndex++
        }

        if(text != textElement.text){
            textElement.text = text
            onTextChange?.invoke(textElement.text)
        }
    }

    fun onKeyHeld(i: Int){
        if(i == KeyEvent.VK_LEFT && cursorIndex > 0){ cursorIndex--; }
        else if(i == KeyEvent.VK_RIGHT && cursorIndex < textElement.text.length){ cursorIndex++; }
        else if(i == KeyEvent.VK_PAGE_UP){ cursorIndex = 0; }
        else if(i == KeyEvent.VK_PAGE_DOWN){ moveCursorToEnd(); }
    }
}
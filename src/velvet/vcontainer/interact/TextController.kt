package velvet.vcontainer.interact

import velvet.vcontainer.velement.BasicTextElement
import java.awt.event.KeyEvent

class TextController(private val basicTextElement: BasicTextElement) {

    var cursorIndex = basicTextElement.text.length

    fun moveCursorToEnd(){
        cursorIndex = basicTextElement.text.length
    }

    fun onCharTyped(c: Char) {
        var text = basicTextElement.text

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
            }
            cursorIndex++
        } else {
            text = text.substring(0, cursorIndex) + c + text.substring(cursorIndex)
            cursorIndex++
        }

        basicTextElement.text = text
    }

    fun onKeyPressed(i: Int){
        if(i == KeyEvent.VK_LEFT && cursorIndex > 0){ cursorIndex--; }
        else if(i == KeyEvent.VK_RIGHT && cursorIndex < basicTextElement.text.length){ cursorIndex++; }
        else if(i == KeyEvent.VK_PAGE_UP){ cursorIndex = 0; }
        else if(i == KeyEvent.VK_PAGE_DOWN){ moveCursorToEnd(); }
    }
}
package core.main;

import java.awt.event.KeyEvent;

public enum Key {
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT),
    SPACE(KeyEvent.VK_SPACE);
    
    public int code;
    
    Key(int code){
        this.code = code;
    }
}
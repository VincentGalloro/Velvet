package core.main;

import java.awt.event.KeyEvent;

public enum Key {
    UP(KeyEvent.VK_W),
    DOWN(KeyEvent.VK_S),
    LEFT(KeyEvent.VK_A),
    RIGHT(KeyEvent.VK_D);
    
    int code;
    
    Key(int code){
        this.code = code;
    }
}
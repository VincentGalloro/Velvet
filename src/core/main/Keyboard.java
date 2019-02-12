package core.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
    
    private boolean[] keys, keysLast, keysPressed, keysReleased;
    
    public Keyboard(){
        keys = new boolean[256];
        keysLast = new boolean[256];
        keysPressed = new boolean[256];
        keysReleased = new boolean[256];
    }
    
    public void update(){
        for(int i = 0; i < keys.length; i++){
            keysPressed[i] = keys[i] && !keysLast[i];
            keysReleased[i] = !keys[i] && keysLast[i];
            keysLast[i] = keys[i];
        }
    }
    
    public boolean isDown(Key k){ return keys[k.code]; }
    public boolean isPressed(Key k){ return keysPressed[k.code]; }
    public boolean isReleased(Key k){ return keysReleased[k.code]; }
    
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
    
}
package velvet.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Keyboard implements KeyListener{
    
    private final boolean[] keys, keysLast, keysPressed, keysReleased;
    private String deltaText, textTyped;
    private ArrayList<Integer> pressedLog, deltaHeld, heldLog, releasedLog;
    
    public Keyboard(){
        keys = new boolean[256];
        keysLast = new boolean[256];
        keysPressed = new boolean[256];
        keysReleased = new boolean[256];
        pressedLog = new ArrayList<>();
        deltaHeld = new ArrayList<>();
        heldLog = new ArrayList<>();
        releasedLog = new ArrayList<>();
        deltaText = "";
        textTyped = "";
    }
    
    public void update(){
        pressedLog.clear();
        releasedLog.clear();
        heldLog.clear();
        for(int i = 0; i < keys.length; i++){
            keysPressed[i] = keys[i] && !keysLast[i];
            keysReleased[i] = !keys[i] && keysLast[i];
            keysLast[i] = keys[i];

            if(keysPressed[i]){ pressedLog.add(i); }
            if(keysReleased[i]){ releasedLog.add(i); }
        }
        textTyped = deltaText;
        deltaText = "";

        heldLog = deltaHeld;
        deltaHeld = new ArrayList<>();
    }
    
    public boolean isDown(int code){ return keys[code]; }
    public boolean isPressed(int code){ return keysPressed[code]; }
    public boolean isReleased(int code){ return keysReleased[code]; }
    
    public ArrayList<Integer> getPressedLog(){ return pressedLog; }
    public ArrayList<Integer> getHeldLog(){ return heldLog; }
    public ArrayList<Integer> getReleasedLog(){ return releasedLog; }
    public String getTextTyped(){ return textTyped; }
    
    public void keyTyped(KeyEvent e) {
        deltaText += e.getKeyChar();
    }
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < 256){ keys[e.getKeyCode()] = true; }
        deltaHeld.add(e.getKeyCode());
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() < 256){ keys[e.getKeyCode()] = false; }
    }
    
}
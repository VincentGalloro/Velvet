package core.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Keyboard implements KeyListener{
    
    private final boolean[] keys, keysLast, keysPressed, keysReleased;
    private String deltaText, textTyped;
    private ArrayList<Integer> pressedLog, deltaPressed;
    
    public Keyboard(){
        keys = new boolean[256];
        keysLast = new boolean[256];
        keysPressed = new boolean[256];
        keysReleased = new boolean[256];
        pressedLog = new ArrayList<>();
        deltaPressed = new ArrayList<>();
        deltaText = "";
        textTyped = "";
    }
    
    public void update(){
        for(int i = 0; i < keys.length; i++){
            keysPressed[i] = keys[i] && !keysLast[i];
            keysReleased[i] = !keys[i] && keysLast[i];
            keysLast[i] = keys[i];
        }
        pressedLog = deltaPressed;
        deltaPressed = new ArrayList<>();
        textTyped = deltaText;
        deltaText = "";
    }
    
    public boolean isDown(Key k){ return keys[k.code]; }
    public boolean isPressed(Key k){ return keysPressed[k.code]; }
    public boolean isReleased(Key k){ return keysReleased[k.code]; }
    
    public ArrayList<Integer> getPressedLog(){ return pressedLog; }
    public String getTextTyped(){ return textTyped; }
    
    public void keyTyped(KeyEvent e) {
        deltaText += e.getKeyChar();
    }
    public void keyPressed(KeyEvent e) {
        deltaPressed.add(e.getKeyCode());
        if(e.getKeyCode() < 256){ keys[e.getKeyCode()] = true; }
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() < 256){ keys[e.getKeyCode()] = false; }
    }
    
}
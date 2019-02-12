package core.main;

import core.main.structs.Vector;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;

public class Mouse implements MouseListener, MouseMotionListener{
    
    public static final int LEFT = 0, RIGHT = 1, MIDDLE = 2;
    private final int[] BUTTON_CODES = {MouseEvent.BUTTON1, MouseEvent.BUTTON3, MouseEvent.BUTTON2};
    
    private boolean[] buttons, buttonsPressed, buttonsLast;
    private Vector pos;
    
    public Mouse(){
        pos = new Vector();
        buttons = new boolean[BUTTON_CODES.length]; 
        buttonsPressed = new boolean[BUTTON_CODES.length];
        buttonsLast = new boolean[BUTTON_CODES.length];
    }
    
    public void update(){
        for(int i = 0; i < BUTTON_CODES.length; i++){
            buttonsPressed[i] = !buttonsLast[i] && buttons[i];
            buttonsLast[i] = buttons[i]; 
        }
    }
    
    public boolean isDown(int b){ return buttons[b]; }
    public boolean isPressed(int b){ return buttonsPressed[b]; }
    
    public Vector getPos(){ return new Vector(pos); }
    public Vector getTransformedPos(AffineTransform at){ return pos.transform(at); }
    
    public void mousePressed(MouseEvent e) {
        for(int i = 0; i < BUTTON_CODES.length; i++){
            if(e.getButton()== BUTTON_CODES[i]){
                buttons[i] = true;
                break;
            }
        }
    }
    public void mouseReleased(MouseEvent e) {
        for(int i = 0; i < BUTTON_CODES.length; i++){
            if(e.getButton()== BUTTON_CODES[i]){
                buttons[i] = false;
                break;
            }
        }
    }
    public void mouseDragged(MouseEvent e) { pos.x = e.getX(); pos.y = e.getY(); }
    public void mouseMoved(MouseEvent e) { pos.x = e.getX(); pos.y = e.getY(); }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
}

package core.main;

import core.main.structs.Vector;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{
    
    public static final int LEFT = 0, RIGHT = 1, MIDDLE = 2;
    
    private final int[] BUTTON_CODES = {MouseEvent.BUTTON1, MouseEvent.BUTTON3, MouseEvent.BUTTON2};
    
    private final boolean[] buttons, buttonsPressed, buttonsLive, buttonsReleased;
    private final Vector pos;
    private int deltaScroll, scrollAmount;
    
    public Mouse(){
        pos = new Vector();
        buttons = new boolean[BUTTON_CODES.length]; 
        buttonsPressed = new boolean[BUTTON_CODES.length];
        buttonsReleased = new boolean[BUTTON_CODES.length];
        buttonsLive = new boolean[BUTTON_CODES.length];
    }
    
    public void update(){
        for(int i = 0; i < BUTTON_CODES.length; i++){
            boolean b = buttonsLive[i]; 
            buttonsPressed[i] = !buttons[i] && b;
            buttonsReleased[i] = buttons[i] && !b;
            buttons[i] = b;
        }
        scrollAmount = deltaScroll;
        deltaScroll = 0;
    }
    
    public boolean isDown(int b){ return buttons[b]; }
    public boolean isPressed(int b){ return buttonsPressed[b]; }
    public boolean isReleased(int b){ return buttonsReleased[b]; }
    
    public Vector getPos(){ return new Vector(pos); }
    public Vector getTransformedPos(AffineTransform at){ return pos.transform(at); }
    
    public int getScrollAmount(){ return scrollAmount; }
    
    public void mousePressed(MouseEvent e) {
        for(int i = 0; i < BUTTON_CODES.length; i++){
            if(e.getButton()== BUTTON_CODES[i]){
                buttonsLive[i] = true;
                break;
            }
        }
    }
    public void mouseReleased(MouseEvent e) {
        for(int i = 0; i < BUTTON_CODES.length; i++){
            if(e.getButton()== BUTTON_CODES[i]){
                buttonsLive[i] = false;
                break;
            }
        }
    }
    public void mouseDragged(MouseEvent e) { pos.x = e.getX(); pos.y = e.getY(); }
    public void mouseMoved(MouseEvent e) { pos.x = e.getX(); pos.y = e.getY(); }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseWheelMoved(MouseWheelEvent e) {
        deltaScroll += e.getWheelRotation();
    }
}

package velvet.main;

import velvet.structs.Vector;

import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener{
    
    public static final int LEFT = 0, RIGHT = 1, MIDDLE = 2;
    
    private final int[] BUTTON_CODES = {MouseEvent.BUTTON1, MouseEvent.BUTTON3, MouseEvent.BUTTON2};
    
    private final boolean[] buttons, buttonsPressed, buttonsLive, buttonsReleased;
    private Vector pos, livePos;
    public Vector scalingFactor;
    private int deltaScroll, scrollAmount;
    
    public Mouse(){
        pos = new Vector();
        livePos = new Vector();
        scalingFactor = new Vector(1);
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
        pos = livePos.times(scalingFactor);
        scrollAmount = deltaScroll;
        deltaScroll = 0;
    }
    
    public boolean isDown(int b){ return buttons[b]; }
    public boolean isPressed(int b){ return buttonsPressed[b]; }
    public boolean isReleased(int b){ return buttonsReleased[b]; }
    
    public Vector getPos(){ return pos; }
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
    public void mouseDragged(MouseEvent e) { livePos = new Vector(e.getX(), e.getY()); }
    public void mouseMoved(MouseEvent e) { livePos = new Vector(e.getX(), e.getY()); }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseWheelMoved(MouseWheelEvent e) {
        deltaScroll += e.getWheelRotation();
    }
}

package core.main.ui.active.impl;

import core.main.Key;
import core.main.Keyboard;
import core.main.VGraphics;
import core.main.smooth.SmoothVector;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.IRenderable;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.ITextable;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class TextEntry implements IUpdateable, IRenderable{

    private static final int CURSOR_FLASH = 30;
    
    private final Keyboard keyboard;
    private final ITextable textable;
    private final FocusObserver focusObserver;
    private SmoothVector cursorPos;
    private int cursorIndex, cursorFlash;
    private boolean renderCursor;
    
    public TextEntry(Keyboard keyboard, ITextable textable){
        this.keyboard = keyboard;
        this.textable = textable;
        this.focusObserver = new FocusObserver(textable);
        setCursor(true);
        cursorPos = new SmoothVector(Motion.swish(8));
    }
    
    private void setCursor(boolean b){
        renderCursor = b;
        cursorFlash = CURSOR_FLASH;
    }
    
    public void update(AffineTransform at){
        if(!focusObserver.isFocussed()){ 
            setCursor(true);
            return; 
        }
        
        cursorFlash--;
        if(cursorFlash <= 0){
            setCursor(!renderCursor);
        }
        
        if(!keyboard.getTextTyped().isEmpty() || !keyboard.getPressedLog().isEmpty()){
            setCursor(true);
        }
        
        String text = textable.getText();
        int lastCursorIndex = cursorIndex;
        if(!keyboard.getTextTyped().isEmpty()){
            for(char c : keyboard.getTextTyped().toCharArray()){
                if(c == '\b'){ //backspace
                    if(cursorIndex > 0){ 
                        text = text.substring(0, cursorIndex-1) + text.substring(cursorIndex);
                        cursorIndex--;
                    }
                }
                else if(c == (char)0x7F){ //delete
                    if(cursorIndex < text.length()){
                        text = text.substring(0, cursorIndex) + text.substring(cursorIndex+1);
                    }
                }
                else if(c == '\n'){ //enter
                    if(textable.supportsNewline()){
                        text = text.substring(0, cursorIndex) + c + text.substring(cursorIndex);
                    }
                    cursorIndex++;
                }
                else{
                    text = text.substring(0, cursorIndex) + c + text.substring(cursorIndex);
                    cursorIndex++;
                }
            }
            textable.setText(text);
        }
        for(Integer i : keyboard.getPressedLog()){
            if(i == Key.LEFT.code && cursorIndex > 0){ cursorIndex--; }
            else if(i == Key.RIGHT.code && cursorIndex < text.length()){ cursorIndex++; }
        }
        
        if(cursorIndex != lastCursorIndex || cursorPos.getPos()==null){
            cursorPos.setPos(new Vector().transform(textable.getCharTransform(cursorIndex)));
        }
        cursorPos.update();
    }
    
    public void render(VGraphics g){
        if(renderCursor && focusObserver.isFocussed()){
            g.save();
            g.translate(cursorPos.getSmooth());
            g.setColor(Color.BLACK);
            g.draw(new Line2D.Double(0, 0, 0, -20));
            g.reset();
        }
    }
}
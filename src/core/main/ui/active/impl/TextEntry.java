package core.main.ui.active.impl;

import core.main.Keyboard;
import core.main.VGraphics;
import core.main.ui.active.IRenderable;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.ITextable;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class TextEntry implements IUpdateable, IRenderable{

    private Keyboard keyboard;
    private ITextable textable;
    private int cursorIndex;
    
    public TextEntry(Keyboard keyboard, ITextable textable){
        this.keyboard = keyboard;
        this.textable = textable;
    }
    
    public void update(AffineTransform at){
        String text = textable.getText();
        if(!keyboard.getTextTyped().isEmpty()){
            for(char c : keyboard.getTextTyped().toCharArray()){
                if(c == '\b'){
                    if(!text.isEmpty()){ text = text.substring(0, text.length()-1); }
                }
                else if(c == '\n'){
                    if(textable.supportsNewline()){ text += c; }
                }
                else{
                    text += c;
                }
                cursorIndex++;
            }
            textable.setText(text);
        }
    }
    
    public void render(VGraphics g){
        g.save();
        g.transform(textable.getCharTransform(cursorIndex));
        g.setColor(Color.BLACK);
        g.draw(new Line2D.Double(0, 0, 0, -20));
        g.reset();
    }
}
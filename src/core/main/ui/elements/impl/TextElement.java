package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicElement;
import core.main.ui.elements.ITextable;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextElement extends BasicElement implements ITextable{

    public static class Builder extends BasicElement.Builder{

        private final TextElement text;
                
        public Builder() {
            super(new TextElement());
            text = (TextElement)create();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("text")){ text.text = value; }
            if(field.equals("color")){ text.color = toColor(value); }
        }
    }
        
    private static final Font font = new Font("Arial", Font.PLAIN, 24);
    
    private String text;
    private FontMetrics fontMetrics;
    private Color color;
    
    public TextElement(){ 
        BufferedImage i = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = i.createGraphics();
        fontMetrics = g.getFontMetrics(font);
        text = "";
        color = Color.BLACK;
    }
    
    public void setText(String t){ text = t; }
    public void setTextColor(Color c){ color = c; }
    
    public Vector getSize() { return new Vector(fontMetrics.stringWidth(text), fontMetrics.getHeight()); }
    public String getText() { return text; } 
    public Color getTextColor() { return color; }
    

    public void render(VGraphics g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, new Vector(0, fontMetrics.getAscent()));
    }
}
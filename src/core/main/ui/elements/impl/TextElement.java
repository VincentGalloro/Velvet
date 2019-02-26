package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.smooth.SmoothColor;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.elements.BasicElement;
import core.main.ui.elements.ITextable;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class TextElement extends BasicElement implements ITextable{

    public static class Builder extends BasicElement.Builder{

        private final TextElement text;
                
        public Builder() {
            super(new TextElement());
            text = (TextElement)get();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("text")){ text.text = value; }
            if(field.equals("color")){ text.color.overrideColor(toColor(value)); }
        }
    }
        
    private static final Font font = new Font("Arial", Font.PLAIN, 24);
    
    private String text;
    private final FontMetrics fontMetrics;
    private final SmoothColor color;
    
    public TextElement(){ 
        BufferedImage i = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        Graphics2D g = i.createGraphics();
        fontMetrics = g.getFontMetrics(font);
        text = "";
        color = new SmoothColor(Color.BLACK, Motion.linear(30));
    }
    
    public void setText(String t){ text = t; }
    public void setTextColor(Color c){ color.setColor(c); }
    public void setTextSmoothColor(Color c){ color.setSmooth(c); }
    
    public void onUpdate(AffineTransform at){
        color.update();
    }
    
    public Vector getSize() { return new Vector(fontMetrics.stringWidth(text), fontMetrics.getAscent()+fontMetrics.getDescent()); }
    public String getText() { return text; } 
    public Color getTextColor() { return color.getColor(); }
    
    public void render(VGraphics g) {
        g.setColor(color.getSmooth());
        g.setFont(font);
        g.drawString(text, new Vector(0, fontMetrics.getAscent()));
    }
}
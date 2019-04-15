package core.main.ui.elements;

import core.main.VGraphics;
import core.main.ui.active.IFontMetricsEventable;
import core.main.ui.active.ITextEventable;
import core.main.ui.active.impl.TextEntry;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class BasicTextable extends BasicElement implements ITextable{
    
    public class Builder extends BasicElement.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("path")){
                try {
                    text = new Scanner(new File(value)).useDelimiter("\\Z").next();
                } catch (FileNotFoundException ex) {
                    System.out.println("COULD NOT FIND TEXT FILE: "+value);
                }
            }
            if(field.equals("text")){ text = value; }
            if(field.equals("text color")){ color = toColor(value); }
            if(field.equals("font size")){ font = font.deriveFont(Float.parseFloat(value)); }
            if(field.equals("style") && value.equals("bold")){ font = font.deriveFont(Font.BOLD); }
            if(field.equals("add")){
                if(value.equals("text entry")){
                    TextEntry te = new TextEntry(BasicTextable.this);
                }
            }
        }
    }
    
    private ArrayList<ITextEventable> textHandlers;
    private ArrayList<IFontMetricsEventable> fontMetricsHandlers;
    protected Font font;
    protected String text;
    protected FontMetrics fontMetrics;
    protected Color color;
    
    public BasicTextable(){
        textHandlers = new ArrayList<>();
        fontMetricsHandlers = new ArrayList<>();
        
        font = new Font("Arial", Font.PLAIN, 24);;
        Canvas c = new Canvas();
        fontMetrics = c.getFontMetrics(font);
        text = "";
        color = Color.BLACK;
        
        addPostRenderHandler(this::setupRender);
    }
    
    public final void addTextChangeHandler(ITextEventable e){ textHandlers.add(e); }
    public final void addFontMetricsChangeHandler(IFontMetricsEventable e){ fontMetricsHandlers.add(e); }
    
    public final void setText(String t){
        text = t; 
        for(ITextEventable e : textHandlers){ e.onText(text); }
    }
    public final void setTextColor(Color c){ color = c; }
    public final void setFontMetrics(FontMetrics fm){ 
        fontMetrics = fm; 
        for(IFontMetricsEventable e : fontMetricsHandlers){ e.onFontMetrics(fm); }
    }
    
    public final String getText() { return text; } 
    public final Color getTextColor() { return color; }
    
    private final void setupRender(VGraphics g){
        g.setColor(color);
        g.setFont(font);
        g.save();
        g.setTransform(new AffineTransform());
        setFontMetrics(g.getFontMetrics());
        g.reset();
    }
}
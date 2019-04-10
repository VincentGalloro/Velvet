package core.main;

import core.main.structs.Vector;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class VGraphics {

    private Graphics2D g;
    private ArrayList<Graphics2D> graphicsStack;
    private ArrayList<AffineTransform> saves;
    
    public VGraphics(Graphics2D g){
        graphicsStack = new ArrayList<>();
        graphicsStack.add(g);
        this.g = g;
        saves = new ArrayList<>();
    }
    
    public void save(){ saves.add(g.getTransform()); }
    public void reset(){ g.setTransform(saves.remove(saves.size()-1)); }
    
    public void setTransform(AffineTransform at){ g.setTransform(at); }
    public AffineTransform getTransform(){ return g.getTransform(); }
    
    public void subGraphics(Graphics2D g){ graphicsStack.add(g); this.g = g; }
    public void resetGraphics(){ 
        graphicsStack.remove(graphicsStack.size()-1);
        this.g = graphicsStack.get(graphicsStack.size()-1); 
    }
    
    public FontMetrics getFontMetrics(){ return g.getFontMetrics(); }
    public Composite getComposite(){ return g.getComposite(); }
    
    public void translate(Vector v){ g.translate(v.x, v.y); }
    public void scale(double d){ g.scale(d, d); }
    public void scale(Vector v){ g.scale(v.x, v.y); }
    public void rotate(double a){ g.rotate(a); }
    public void rotate(double a, Vector p){ g.rotate(a, p.x, p.y); }
    public void transform(AffineTransform at){ g.transform(at); }
    
    public void setComposite(Composite c){ g.setComposite(c); }
    public void setStroke(Stroke s){ g.setStroke(s); }
    public void resetStroke(){ g.setStroke(new BasicStroke(1)); }
    public void setColor(Color c){ g.setColor(c); }
    public void setFont(Font f){ g.setFont(f); }
    
    public void draw(Shape s){ g.draw(s); }
    public void fill(Shape s){ g.fill(s); }
    
    public void drawImage(BufferedImage img, Vector pos){ g.drawImage(img, (int)pos.x, (int)pos.y, null); }
    public void drawImage(BufferedImage img, Vector pos, Vector size){ g.drawImage(img, (int)pos.x, (int)pos.y, (int)size.x, (int)size.y, null); }
    public void drawImage(BufferedImage img, AffineTransform at){ g.drawImage(img, at, null); }
    
    public void drawString(String s, Vector pos){ g.drawString(s, (float)pos.x, (float)pos.y); }
}
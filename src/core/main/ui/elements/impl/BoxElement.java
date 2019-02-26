package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.smooth.SmoothColor;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.elements.BasicContainer;
import core.main.ui.elements.IBoxable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class BoxElement extends BasicContainer implements IBoxable{
    
    public static class Builder extends BasicContainer.Builder{

        private final BoxElement box;
                
        public Builder() {
            super(new BoxElement());
            box = (BoxElement)get();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("outline")){ box.outline.overrideColor(toColor(value)); }
            if(field.equals("fill")){ box.fill.overrideColor(toColor(value)); }
            if(field.equals("thickness")){ box.thickness = Float.parseFloat(value); }
        }
    }
    
    private final SmoothColor outline, fill;
    private float thickness;
    
    public BoxElement(){
        outline = new SmoothColor(Color.BLACK, Motion.linear(60));
        fill = new SmoothColor(Color.WHITE, Motion.linear(1));
        thickness = 2;
    }
    
    public void setOutlineColor(Color o){ outline.setColor(o); }
    public void setFillColor(Color f){ fill.setColor(f); }
    public void setThickness(float t){ thickness = t; }
    
    public void onUpdate(AffineTransform at){
        outline.update();
        fill.update();
    }
    
    public Vector getSize() {
        if(element == null){ return new Vector(); }
        return element.getSize();
    }
    
    public AffineTransform getTransform(){ return new AffineTransform(); }

    public void render(VGraphics g) {
        Vector size = getSize();
        
        g.setColor(fill.getSmooth());
        g.fill(new Rectangle2D.Double(0, 0, size.x, size.y));
        
        super.render(g);
        
        g.setColor(outline.getSmooth());
        g.setStroke(new BasicStroke(thickness));
        g.draw(new Rectangle2D.Double(0, 0, size.x, size.y));
    }   
}

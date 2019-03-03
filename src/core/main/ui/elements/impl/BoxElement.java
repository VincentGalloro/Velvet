package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.adapters.impl.BoxOutlineAdapter;
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
            if(field.equals("outline")){ box.outline = toColor(value); }
            if(field.equals("fill")){ box.fill = toColor(value); }
            if(field.equals("thickness")){ box.thickness = Float.parseFloat(value); }
        }
    }
    
    private Color outline, fill;
    private float thickness;
    
    public BoxElement(){
        outline = Color.BLACK;
        thickness = 2;
    }
    
    public void setOutlineColor(Color o){ outline = o; }
    public void setFillColor(Color f){ fill = f; }
    public void setThickness(float t){ thickness = t; }
    
    public BoxOutlineAdapter getOutlineColorAdapter(){ return new BoxOutlineAdapter(this); }
    
    public Vector getSize() {
        if(element == null){ return new Vector(); }
        return element.getSize();
    }
    
    public AffineTransform getTransform(){ return new AffineTransform(); }

    public void render(VGraphics g) {
        Vector size = getSize();
        if(fill != null){
            g.setColor(fill);
            g.fill(new Rectangle2D.Double(0, 0, size.x, size.y));
        }
        super.render(g);
        if(outline != null){
            g.setColor(outline);
            g.setStroke(new BasicStroke(thickness));
            g.draw(new Rectangle2D.Double(0, 0, size.x, size.y));
        }
    }   
}

package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicContainer;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IElementBuilder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class BoxElement extends BasicContainer implements IBoxable{
    
    public class Builder extends BasicContainer.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("outline color")){ outline = toColor(value); }
            if(field.equals("fill color")){ fill = toColor(value); }
            if(field.equals("outline thickness")){ thickness = Float.parseFloat(value); }
        }
    }
    
    private Color outline, fill;
    private float thickness;
    
    public BoxElement(){
        outline = Color.BLACK;
        thickness = 2;
        
        addPreRenderHandler(this::preRender);
        addPostRenderHandler(this::postRender);
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public void setOutlineColor(Color o){ outline = o; }
    public void setFillColor(Color f){ fill = f; }
    public void setThickness(float t){ thickness = t; }
    
    public Color getOutlineColor(){ return outline; }
    public Color getFillColor(){ return fill; }
    
    public Vector getSize() {
        if(element == null){ return new Vector(); }
        return element.getSize();
    }

    private void preRender(VGraphics g) {
        Vector size = getSize();
        if(fill != null){
            g.setColor(fill);
            g.fill(new Rectangle2D.Double(0, 0, size.x, size.y));
        }
    }    
    
    private void postRender(VGraphics g){
        Vector size = getSize();
        if(outline != null){
            g.setColor(outline);
            g.setStroke(new BasicStroke(thickness));
            g.draw(new Rectangle2D.Double(0, 0, size.x, size.y));
        }
    }   
}

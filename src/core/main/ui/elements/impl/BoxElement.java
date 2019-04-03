package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IRenderable;
import core.main.ui.elements.BasicContainer;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IElementBuilder;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class BoxElement extends BasicContainer implements IBoxable{
    
    public class Builder extends BasicContainer.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("outline color")){ outline = toColor(value); }
            if(field.equals("fill color")){ fill = toColor(value); }
            if(field.equals("outline thickness")){ thickness = Float.parseFloat(value); }
            if(field.equals("rounding")){ rounding = Double.parseDouble(value); }
        }
        
        //temporary setting - current only used by scroll column
        public void preRenderOutline(){
            removePostRenderHandler(outlineRender);
            addPreRenderHandler(outlineRender);
        }
    }
    
    private final IRenderable fillRender, outlineRender;
    private Color outline, fill;
    private float thickness;
    private Double rounding;
    
    public BoxElement(){
        outline = Color.BLACK;
        thickness = 2;
        
        fillRender = this::fillRender;
        outlineRender = this::outlineRender;
        addPreRenderHandler(fillRender);
        addPostRenderHandler(outlineRender);
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public void setOutlineColor(Color o){ outline = o; }
    public void setFillColor(Color f){ fill = f; }
    public void setOutlineThickness(float t){ thickness = t; }
    public void setRounding(Double r){ rounding = r; }
    
    public Color getOutlineColor(){ return outline; }
    public Color getFillColor(){ return fill; }
    
    public Vector getSize() {
        if(element == null){ return new Vector(); }
        return element.getSize();
    }
    
    private Shape getShape(){
        Vector size = getSize();
        if(rounding==null){ return new Rectangle2D.Double(0, 0, size.x, size.y); }
        return new RoundRectangle2D.Double(0, 0, size.x, size.y, rounding, rounding);
    }

    private void fillRender(VGraphics g) {
        if(fill != null){
            g.setColor(fill);
            g.fill(getShape());
        }
    }    
    
    private void outlineRender(VGraphics g){
        if(outline != null){
            g.setColor(outline);
            g.setStroke(new BasicStroke(thickness));
            g.draw(getShape());
            g.resetStroke();
        }
    }   
}

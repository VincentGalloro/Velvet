package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IRenderable;
import java.awt.BasicStroke;
import java.awt.Color;

public abstract class BasicShapeable extends BasicContainer implements IShapeable{
    
    public class Builder extends BasicContainer.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("outline color")){ outline = toColor(value); }
            if(field.equals("fill color")){ fill = toColor(value); }
            if(field.equals("outline thickness")){ thickness = Float.parseFloat(value); }
        }
        
        //temporary setting - currently only used by scroll column
        public void preRenderOutline(){
            removePostRenderHandler(outlineRender);
            addPreRenderHandler(outlineRender);
        }
    }
    
    private final IRenderable fillRender, outlineRender;
    private Color outline, fill;
    private float thickness;
    
    public BasicShapeable(){
        outline = Color.BLACK;
        thickness = 2;
        
        fillRender = this::fillRender;
        outlineRender = this::outlineRender;
        addPreRenderHandler(fillRender);
        addPostRenderHandler(outlineRender);
    }
    
    public final void setOutlineColor(Color o){ outline = o; }
    public final void setFillColor(Color f){ fill = f; }
    public final void setOutlineThickness(float t){ thickness = t; }
    
    public final Color getOutlineColor(){ return outline; }
    public final Color getFillColor(){ return fill; }
    
    public final Vector getSize() {
        if(element == null){ return new Vector(); }
        return element.getSize();
    }

    private final void fillRender(VGraphics g) {
        if(fill != null){
            g.setColor(fill);
            g.fill(getShape());
        }
    }    
    
    private final void outlineRender(VGraphics g){
        if(outline != null){
            g.setColor(outline);
            g.setStroke(new BasicStroke(thickness));
            g.draw(getShape());
            g.resetStroke();
        }
    }   
}

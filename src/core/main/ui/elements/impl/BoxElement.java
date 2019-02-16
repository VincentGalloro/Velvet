package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicContainer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class BoxElement extends BasicContainer{

    private Color outline, fill;
    
    public BoxElement(){
        outline = Color.BLACK;
    }
    
    public void setOutlineColor(Color o){ outline = o; }
    public void setFillColor(Color f){ fill = f; }
    
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
        if(element != null){
            element.render(g);
        }
        if(outline != null){
            g.setColor(outline);
            g.setStroke(new BasicStroke(2f));
            g.draw(new Rectangle2D.Double(0, 0, size.x, size.y));
        }
    }   
}

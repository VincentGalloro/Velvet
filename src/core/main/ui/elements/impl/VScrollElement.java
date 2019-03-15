
package core.main.ui.elements.impl;

import core.main.Mouse;
import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.impl.DragObserver;
import core.main.ui.active.impl.VerticalScroller;
import core.main.ui.elements.BasicScrollable;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class VScrollElement extends BasicScrollable{

    public static class Builder extends BasicScrollable.Builder{
        
        public Builder(Mouse mouse) { 
            super(new VScrollElement()); 
            VScrollElement vScroll = (VScrollElement)get();
            
            DragObserver dragObserver = new DragObserver(vScroll);
            VerticalScroller scroller = new VerticalScroller(vScroll, mouse, dragObserver);
            vScroll.addUpdateHandler(scroller);
        }
    }
    
    public Vector getSize() {
        if(element == null){
            return new Vector(0, length);
        }
        return new Vector(element.getSize().x, length + element.getSize().y);
    }

    public AffineTransform getTransform() {
        AffineTransform at = new AffineTransform();
        at.translate(0, length * delta);
        return at;
    }
    
    public void render(VGraphics g){
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        Vector eSize = element.getSize();
        g.draw(new Line2D.Double(eSize.x/2, eSize.y/2, eSize.x/2, length + eSize.y/2));
        
        super.render(g);
    }
}

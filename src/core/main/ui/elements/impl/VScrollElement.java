
package core.main.ui.elements.impl;

import core.main.Mouse;
import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.impl.DragObserver;
import core.main.ui.active.impl.VerticalScroller;
import core.main.ui.active.impl.WheelScroller;
import core.main.ui.elements.BasicScrollable;
import core.main.ui.elements.IElementBuilder;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class VScrollElement extends BasicScrollable{
    
    public VScrollElement(Mouse mouse){     
        addTransformHandler(this::transform);
        addPreRenderHandler(this::preRender);

        DragObserver dragObserver = new DragObserver(this);
        VerticalScroller scroller = new VerticalScroller(this, mouse, dragObserver);
        addUpdateHandler(scroller);

        addMouseScrollHandler(new WheelScroller(this, 0.05));
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public Vector getSize() {
        if(element == null){
            return new Vector(0, length);
        }
        return new Vector(element.getSize().x, length + element.getSize().y);
    }

    private AffineTransform transform() {
        AffineTransform at = new AffineTransform();
        at.translate(0, length * delta);
        return at;
    }
    
    private void preRender(VGraphics g){
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        Vector eSize = element.getSize();
        g.draw(new Line2D.Double(eSize.x/2, eSize.y/2, eSize.x/2, length + eSize.y/2));
    }
}

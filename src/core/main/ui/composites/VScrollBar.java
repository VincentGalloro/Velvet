
package core.main.ui.composites;

import core.main.Mouse;
import core.main.structs.Vector;
import core.main.ui.elements.IElementBuilder;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class VScrollBar extends BasicScrollBar{
    
    public VScrollBar(Mouse mouse){
        MouseScroller scroller = new MouseScroller(at -> mouse.getPos().inverseTransform(at).y);
        addUpdateHandler(scroller);

        addMouseScrollHandler(new WheelScroller(0.05, false));
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public Vector getSize() {
        return new Vector(knob.getSize().x, length);
    }

    protected final AffineTransform getTransform() {
        AffineTransform at = new AffineTransform();
        at.translate(0, length * getScrollablePercentage() * delta);
        return at;
    }
    
    public void setScrollablePercentage(double d){
        d = Math.max(Math.min(d, 1), 0);
        knobSizeable.setSize(new Vector(knob.getSize().x, length*(1-d)));
    }

    public double getScrollablePercentage() {
        return Math.max(1 - knob.getSize().y/length, 0);
    }
    
    protected Line2D.Double getLine() {
        double x = knob.getSize().x/2;
        return new Line2D.Double(x, 0, x, length);
    }
}


package core.main.ui.composites;

import core.main.Mouse;
import core.main.structs.Vector;
import core.main.ui.elements.IElementBuilder;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class HScrollBar extends BasicScrollBar{
    
    public HScrollBar(Mouse mouse){
        MouseScroller scroller = new MouseScroller(at -> mouse.getPos().inverseTransform(at).x);
        addUpdateHandler(scroller);

        addMouseScrollHandler(new WheelScroller(0.05, true));
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public Vector getSize() {
        return new Vector(length, knob.getSize().y);
    }

    protected final AffineTransform getTransform() {
        AffineTransform at = new AffineTransform();
        at.translate(length * getScrollablePercentage() * delta, 0);
        return at;
    }
    
    public void setScrollablePercentage(double d) {
        d = Math.max(Math.min(d, 1), 0);
        knobSizeable.setSize(new Vector(length*(1-d), knob.getSize().y));
    }

    public double getScrollablePercentage() {
        return Math.max(1 - knob.getSize().x/length, 0);
    }
    
    protected Line2D.Double getLine() {
        double y = knob.getSize().y/2;
        return new Line2D.Double(0, y, length, y);
    }
}

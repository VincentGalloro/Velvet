
package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicScrollable;
import java.awt.BasicStroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class HScrollElement extends BasicScrollable{

    public static class Builder extends BasicScrollable.Builder{
        public Builder() { super(new HScrollElement()); }
    }
    
    public Vector getSize() {
        if(element == null){
            return new Vector(length, 0);
        }
        return new Vector(length + element.getSize().x, element.getSize().y);
    }

    public AffineTransform getTransform() {
        AffineTransform at = new AffineTransform();
        at.translate(length * delta, 0);
        return at;
    }
    
    public void render(VGraphics g){
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        Vector eSize = element.getSize();
        g.draw(new Line2D.Double(eSize.x/2, eSize.y/2, eSize.x/2 + length, eSize.y/2));
        
        super.render(g);
    }
}

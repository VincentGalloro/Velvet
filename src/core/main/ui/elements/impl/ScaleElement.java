package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.BasicSizeable;
import java.awt.geom.AffineTransform;

public class ScaleElement  extends BasicSizeable{
    
    public static class Builder extends BasicSizeable.Builder{
        public Builder() { super(new ScaleElement()); }
    }
    
    public AffineTransform getTransform(){        
        AffineTransform at = new AffineTransform();
        Vector eSize = element.getSize();

        double scale = size.divide(eSize).min();
        at.translate((size.x - eSize.x*scale)/2, (size.y - eSize.y*scale)/2);
        at.scale(scale, scale);
        
        return at;
    }
}
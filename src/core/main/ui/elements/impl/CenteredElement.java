package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.BasicSizeable;
import java.awt.geom.AffineTransform;

public class CenteredElement extends BasicSizeable{
    
    public static class Builder extends BasicSizeable.Builder{
        public Builder() { super(new CenteredElement()); }
    }
    
    public AffineTransform getTransform(){        
        AffineTransform at = new AffineTransform();
        Vector t = getSize().subtract(element.getSize()).half();
        at.translate(t.x, t.y);
        return at;
    }
}
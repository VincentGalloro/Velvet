package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicContainer;
import java.awt.geom.AffineTransform;

public class PaddingElement extends BasicContainer{

    private double padding;
    
    public void setPadding(double p){ padding = p; }
    
    public Vector getSize() {
        if(element == null){ return new Vector(); }
        return element.getSize().add(padding*2);
    }
    
    public AffineTransform getTransform(){
        AffineTransform at = new AffineTransform();
        at.translate(padding, padding);
        return at;
    }

    public void render(VGraphics g) {
        if(element != null){
            g.save();
            g.transform(getTransform());
            element.render(g);
            g.reset();
        }
    }
}
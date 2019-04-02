package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.BasicSizeable;
import core.main.ui.elements.IElementBuilder;
import java.awt.geom.AffineTransform;

public class ScaleElement extends BasicSizeable{
    
    public ScaleElement(){
        addTransformHandler(this::transform);
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    private AffineTransform transform(){        
        AffineTransform at = new AffineTransform();
        Vector eSize = element.getSize();

        double scale = size.divide(eSize).min();
        at.translate((size.x - eSize.x*scale)/2, (size.y - eSize.y*scale)/2);
        at.scale(scale, scale);
        
        return at;
    }
}
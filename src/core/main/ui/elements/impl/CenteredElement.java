package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.BasicSizeable;
import core.main.ui.elements.IElementBuilder;
import java.awt.geom.AffineTransform;

public class CenteredElement extends BasicSizeable{
    
    public CenteredElement(){
        addTransformHandler(this::transform);
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    private AffineTransform transform(){        
        AffineTransform at = new AffineTransform();
        Vector t = getSize().subtract(element.getSize()).half();
        at.translate(t.x, t.y);
        return at;
    }
}
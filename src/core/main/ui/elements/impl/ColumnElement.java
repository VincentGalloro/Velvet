package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IElementBuilder;
import core.main.ui.elements.SeriesListContainer;
import java.awt.geom.AffineTransform;

public class ColumnElement extends SeriesListContainer{
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public Vector getSize() {
        Vector size = new Vector();
        for(IElement e : elements){
            if(e.getSize().x > size.x){ size.x = e.getSize().x; }
            size.y += e.getSize().y;
        }
        size.y += seperation * (elements.size()-1);
        return size;
    }

    public AffineTransform getTransform(int index) {
        AffineTransform at = new AffineTransform();
        for(int i = 0; i < index; i++){
            at.translate(0, elements.get(i).getSize().y + seperation);
        }
        return at;
    }
}
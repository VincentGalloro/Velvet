package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.IElement;
import core.main.ui.elements.SeriesListContainer;
import java.awt.geom.AffineTransform;

public class RowElement extends SeriesListContainer{
            
    public static class Builder extends SeriesListContainer.Builder{
        public Builder() { super(new RowElement()); }
    }
    
    public Vector getSize() {
        Vector size = new Vector();
        for(IElement e : elements){
            if(e.getSize().y > size.y){ size.y = e.getSize().y; }
            size.x += e.getSize().x;
        }
        size.x += seperation * (elements.size()-1);
        return size;
    }

    public AffineTransform getTransform(int index) {
        AffineTransform at = new AffineTransform();
        for(int i = 0; i < index; i++){
            at.translate(elements.get(i).getSize().x + seperation, 0);
        }
        return at;
    }
}
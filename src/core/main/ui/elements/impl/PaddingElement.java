package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.BasicContainer;
import core.main.ui.elements.IPaddable;
import java.awt.geom.AffineTransform;

public class PaddingElement extends BasicContainer implements IPaddable{

    public static class Builder extends BasicContainer.Builder{

        private final PaddingElement padding;
                
        public Builder() {
            super(new PaddingElement());
            padding = (PaddingElement)get();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("amount")){ padding.padding = Float.parseFloat(value); }
        }
    } 
    
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
}
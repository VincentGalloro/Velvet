package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.BasicContainer;
import core.main.ui.elements.ISizeable;
import java.awt.geom.AffineTransform;

public class CenteredElement extends BasicContainer implements ISizeable{
    
    public static class Builder extends BasicContainer.Builder{

        private final CenteredElement center;
                
        public Builder() {
            super(new CenteredElement());
            center = (CenteredElement)get();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("size")){
                String[] tokens = value.split(",");
                center.size = new Vector(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1])); 
            }
        }
    }
    
    private Vector size;

    public CenteredElement(){
        size = new Vector();
    }
    
    public void setSize(Vector s){ size = s; }
    
    public Vector getSize() { return size; }
    
    public AffineTransform getTransform(){        
        AffineTransform at = new AffineTransform();
        Vector t = getSize().subtract(element.getSize()).half();
        at.translate(t.x, t.y);
        return at;
    }
}
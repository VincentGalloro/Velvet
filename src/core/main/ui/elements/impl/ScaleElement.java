package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.BasicContainer;
import core.main.ui.elements.ISizeable;
import java.awt.geom.AffineTransform;

public class ScaleElement extends BasicContainer implements ISizeable{
    
    public static class Builder extends BasicContainer.Builder{

        private final ScaleElement scale;
                
        public Builder() {
            super(new ScaleElement());
            scale = (ScaleElement)get();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("size")){
                String[] tokens = value.split(",");
                scale.size = new Vector(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1])); 
            }
        }
    }
    
    private Vector size;

    public ScaleElement(){
        size = new Vector();
    }
    
    public void setSize(Vector s){ size = s; }
    
    public Vector getSize() { return size; }
    
    public AffineTransform getTransform(){        
        AffineTransform at = new AffineTransform();
        Vector eSize = element.getSize();

        double scale = size.divide(eSize).min();
        at.translate((size.x - eSize.x*scale)/2, (size.y - eSize.y*scale)/2);
        at.scale(scale, scale);
        
        return at;
    }
}
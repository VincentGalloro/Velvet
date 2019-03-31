package core.main.ui.elements.impl;

import core.main.structs.GridDirection;
import core.main.structs.Vector;
import core.main.ui.elements.BasicContainer;
import core.main.ui.elements.IPaddable;
import java.awt.geom.AffineTransform;

public class PaddingElement extends BasicContainer implements IPaddable{

    public static class Builder extends BasicContainer.Builder{

        private static final String[] DIRS = {"top", "right", "bottom", "left"};
        
        private final PaddingElement padding;
                
        public Builder() {
            super(new PaddingElement());
            padding = (PaddingElement)get();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("padding")){ padding.setPadding(Float.parseFloat(value)); }
            if(field.endsWith(" padding")){
                String[] tokens = field.split(" ");
                for(int i = 0; i < DIRS.length; i++){
                    if(tokens[0].equals(DIRS[i])){
                        padding.setPadding(Float.parseFloat(value), GridDirection.values()[i]);
                        break;
                    }
                }
            }
        }
    } 
    
    private final double[] padding;
    
    public PaddingElement(){
        this.padding = new double[4];
    }
    
    public PaddingElement(double[] padding){
        this.padding = padding;
    }
    
    public void setPadding(double p){
        for(int i = 0; i < 4; i++){ padding[i] = p; }
    }
    public void setPadding(double p, GridDirection dir){
        padding[dir.ordinal()] = p;
    }
    
    public Vector getSize() {
        Vector size = new Vector(padding[GridDirection.LEFT.ordinal()]+padding[GridDirection.RIGHT.ordinal()], 
                padding[GridDirection.UP.ordinal()]+padding[GridDirection.DOWN.ordinal()]);
        
        if(element == null){ return size; }
        return element.getSize().add(size);
    }
    
    public AffineTransform getTransform(){
        AffineTransform at = new AffineTransform();
        at.translate(padding[3], padding[0]);
        return at;
    }
}
package core.main.ui.elements.impl;

import core.main.structs.Vector;
import core.main.ui.elements.BasicShapeable;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IElementBuilder;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class BoxElement extends BasicShapeable implements IBoxable{
    
    public class Builder extends BasicShapeable.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("rounding")){ rounding = Double.parseDouble(value); }
        }
    }
    
    private Double rounding;
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public void setRounding(Double d) { rounding = d; }
    
    public Shape getShape(){
        Vector size = getSize();
        if(rounding==null){ return new Rectangle2D.Double(0, 0, size.x, size.y); }
        return new RoundRectangle2D.Double(0, 0, size.x, size.y, rounding, rounding);
    }
}

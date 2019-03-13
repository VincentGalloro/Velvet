
package core.main.ui.elements;

import core.main.structs.Vector;
import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.CenteredElement;
import java.awt.Color;

public abstract class BasicScrollable extends BasicContainer implements IScrollable{

    public static abstract class Builder extends BasicContainer.Builder{
        
        private final BasicScrollable scrollable;
        
        public Builder(BasicScrollable scrollable) {
            super(scrollable);
            this.scrollable = scrollable;
            
            BoxElement box = new BoxElement();
            ISizeable size = new CenteredElement();
            size.setSize(new Vector(16));
            box.setElement(size);
            
            scrollable.setElement(box);
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("length")){ scrollable.length = Double.parseDouble(value); }
            if(field.equals("color")){ scrollable.color = toColor(value); }
            if(field.equals("thickness")){ scrollable.thickness = Float.parseFloat(value); }
            if(field.equals("delta")){ scrollable.delta = Double.parseDouble(value); }
        }
    }
    
    protected Color color;
    protected double length, delta;
    protected float thickness;
    
    public BasicScrollable(){
        color = Color.BLACK;
        length = 100;
        thickness = 2;
    }

    public void setDelta(double d) { delta = d; }

    public double getDelta() { return delta; }
}

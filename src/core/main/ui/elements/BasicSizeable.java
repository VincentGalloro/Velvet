
package core.main.ui.elements;

import core.main.structs.Vector;

public abstract class BasicSizeable extends BasicContainer implements ISizeable{
    
    public class Builder extends BasicContainer.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("size")){
                String[] tokens = value.split(" ");
                size = new Vector(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1])); 
            }
        }
    }
    
    protected Vector size;

    public BasicSizeable(){
        size = new Vector();
    }
    
    public final void setSize(Vector s){ size = s; }
    
    public final Vector getSize() { return size; }
}

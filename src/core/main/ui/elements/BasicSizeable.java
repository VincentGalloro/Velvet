
package core.main.ui.elements;

import core.main.structs.Vector;

public abstract class BasicSizeable extends BasicContainer implements ISizeable{
    
    public static class Builder extends BasicContainer.Builder{

        private final BasicSizeable sizeable;
                
        public Builder(BasicSizeable sizeable) {
            super(sizeable);
            this.sizeable = sizeable;
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("size")){
                String[] tokens = value.split(" ");
                sizeable.size = new Vector(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1])); 
            }
        }
    }
    
    protected Vector size;

    public BasicSizeable(){
        size = new Vector();
    }
    
    public void setSize(Vector s){ size = s; }
    
    public Vector getSize() { return size; }
}


package core.main.ui.elements;

import core.main.structs.Vector;

public class BasicSizeable extends BasicContainer implements ISizeable{
    
    public class Builder extends BasicContainer.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("size")){ setSize(toVector(value)); }
        }
    }
    
    protected Vector size;

    public BasicSizeable(){
        size = new Vector();
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public final void setSize(Vector s){ size = s; }
    
    public final Vector getSize() { return size; }
}

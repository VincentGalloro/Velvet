
package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BasicListContainer extends BasicElement implements IListContainer{

    public static class Builder extends BasicElement.Builder{
        
        private final BasicListContainer listContainer;
        
        public Builder(BasicListContainer listContainer) {
            super(listContainer);
            this.listContainer = listContainer;
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            //TODO
        }
    }
    
    protected final ArrayList<IElement> elements;
    
    public BasicListContainer(){
        elements = new ArrayList<>();
    }
    
    public final void addElement(IElement e) { elements.add(e); }

    public final int getElementCount() { return elements.size(); }
    public final Iterator<IElement> getElements() { return elements.iterator(); }
    
    public final IElement getHover(Vector mPos){
        for(int i = 0; i < elements.size(); i++){
            IElement cHover = elements.get(i).getHover(mPos.inverseTransform(getTransform(i))); 
            if(cHover != null){ return cHover; }
        }
        return super.getHover(mPos);
    }
    
    public final void render(VGraphics g) {
        for(int i = 0; i < elements.size(); i++){
            g.save();
            g.transform(getTransform(i));
            elements.get(i).render(g);
            g.reset();
        }
    }
}

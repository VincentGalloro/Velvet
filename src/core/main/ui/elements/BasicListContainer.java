
package core.main.ui.elements;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class BasicListContainer extends BasicElement implements IListContainer{

    protected final ArrayList<IElement> elements;
    
    public BasicListContainer(){
        elements = new ArrayList<>();
    }
    
    public final void addElement(IElement e) { elements.add(e); }

    public final int getElementCount() { return elements.size(); }
    public final Iterator<IElement> getElements() { return elements.iterator(); }

    public final void containerUpdate() {
        for(int i = 0; i < elements.size(); i++){
            elements.get(i).update(getTransform(i));
        }
    }
}

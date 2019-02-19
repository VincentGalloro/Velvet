
package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;

public abstract class BasicContainer extends BasicElement implements IContainer{

    protected IElement element;
    
    public final void setElement(IElement e) { element = e; }
    public final IElement getElement() { return element; }
    
    public final IElement getHover(Vector mPos){
        if(element != null){ 
            IElement cHover = element.getHover(mPos.inverseTransform(getTransform())); 
            if(cHover != null){ return cHover; }
        }
        return super.getHover(mPos);
    }
    
    public void render(VGraphics g) {
        if(element != null){
            g.save();
            g.transform(getTransform());
            element.render(g);
            g.reset();
        }
    }
}

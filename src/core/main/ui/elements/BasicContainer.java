
package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import java.awt.geom.AffineTransform;

public abstract class BasicContainer extends BasicElement implements IContainer{

    public static abstract class Builder extends BasicElement.Builder{
        
        private final BasicContainer container;
        
        public Builder(BasicContainer container) {
            super(container);
            this.container = container;
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            //TODO
        }
    }
    
    protected IElement element;
    
    public final void setElement(IElement e) { element = e; }
    
    protected final void containerUpdate(AffineTransform at){
        if(element != null){
            AffineTransform nat = new AffineTransform(at);
            nat.concatenate(getTransform());
            element.update(nat);
        }
    }
    
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

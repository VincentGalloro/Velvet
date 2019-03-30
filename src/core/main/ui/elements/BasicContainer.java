
package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IRenderable;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

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
    
    private final ArrayList<IRenderable> preChildRenderHandlers, postChildRenderHandlers;
    protected IElement element;
    
    public BasicContainer(){
        this.preChildRenderHandlers = new ArrayList<>();
        this.postChildRenderHandlers = new ArrayList<>();
        
        addUpdateHandler(this::childUpdate);
        addPostRenderHandler(this::childRender);
    }
    
    public void addPreChildRenderHandler(IRenderable rendereable){ preChildRenderHandlers.add(rendereable); }
    public void addPostChildRenderHandler(IRenderable rendereable){ postChildRenderHandlers.add(rendereable); }
    
    public final void setElement(IElement e) { element = e; }
    
    protected final void childUpdate(AffineTransform at){
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
    
    protected final void childRender(VGraphics g) {
        if(element != null){
            g.save();
            g.transform(getTransform());
            for(int i = preChildRenderHandlers.size()-1; i>=0; i--){ 
                preChildRenderHandlers.get(i).render(g); 
            }
            element.render(g);
            for(IRenderable r : postChildRenderHandlers){ 
                r.render(g); 
            }
            g.reset();
        }
    }
}

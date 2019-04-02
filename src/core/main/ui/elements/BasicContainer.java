
package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IRenderable;
import core.main.ui.active.ITransformable;
import core.main.ui.active.impl.WindowedView;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public abstract class BasicContainer extends BasicElement implements IContainer{

    public class Builder extends BasicElement.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("strict")){ setStrict(Boolean.parseBoolean(value)); }
        }
    }
    
    private WindowedView windowedView;
    private final ArrayList<IRenderable> preChildRenderHandlers, postChildRenderHandlers;
    private final ArrayList<ITransformable> transformHandlers;
    protected IElement element;
    
    public BasicContainer(){
        this.preChildRenderHandlers = new ArrayList<>();
        this.postChildRenderHandlers = new ArrayList<>();
        this.transformHandlers = new ArrayList<>();
        
        addUpdateHandler(this::childUpdate);
        addPostRenderHandler(this::childRender);
    }
    
    public final void addPreChildRenderHandler(IRenderable rendereable){ preChildRenderHandlers.add(rendereable); }
    public final void addPostChildRenderHandler(IRenderable rendereable){ postChildRenderHandlers.add(rendereable); }
    public final void addTransformHandler(ITransformable transformable){ transformHandlers.add(transformable); }
    
    public final void removePreChildRenderHandler(IRenderable rendereable){ preChildRenderHandlers.remove(rendereable); }
    public final void removePostChildRenderHandler(IRenderable rendereable){ postChildRenderHandlers.remove(rendereable); }
    public final void removeTransformHandler(ITransformable transformable){ transformHandlers.remove(transformable); }
    
    public final void setStrict(boolean strict){
        if(strict){
            if(windowedView == null){
                windowedView = new WindowedView();
                windowedView.apply(this);
            }
        }
        else{
            if(windowedView != null){
                windowedView.detach();
                windowedView = null;
            }
        }
    }
    public final boolean isStrict(){ return windowedView != null; }
    
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
        if(element != null && (!isStrict() || isHovered(mPos))){ 
            IElement cHover = element.getHover(mPos.inverseTransform(getTransform())); 
            if(cHover != null){ return cHover; }
        }
        return super.getHover(mPos);
    }
    
    public final AffineTransform getTransform(){
        AffineTransform at = new AffineTransform();
        for(ITransformable t : transformHandlers){
            at.concatenate(t.getTransform());
        }
        return at;
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

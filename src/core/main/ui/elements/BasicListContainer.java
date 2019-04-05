
package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IListTransformable;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.stream.Stream;

public abstract class BasicListContainer extends BasicElement implements IListContainer{

    public class Builder extends BasicElement.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            //TODO
        }
    }
    
    protected final ArrayList<IElement> elements;
    private final ArrayList<IListTransformable> transformHandlers;
    
    public BasicListContainer(){
        elements = new ArrayList<>();
        
        transformHandlers = new ArrayList<>();
        
        addUpdateHandler(this::childUpdate);
        addPostRenderHandler(this::childRender);
    }
    
    public final void addTransformHandler(IListTransformable transformable){ transformHandlers.add(transformable); }
    
    public final void removeTransformHandler(IListTransformable transformable){ transformHandlers.remove(transformable); }
    
    public final void addElement(IElement e) { elements.add(e); }

    protected final void childUpdate(AffineTransform at){
        for(int i = 0; i < elements.size(); i++){
            AffineTransform nat = new AffineTransform(at);
            nat.concatenate(getTransform(i));
            elements.get(i).update(nat);
        }
    }
    
    public final AffineTransform getTransform(int index){
        AffineTransform at = new AffineTransform();
        for(IListTransformable t : transformHandlers){
            at.concatenate(t.getTransform(index));
        }
        return at;
    }
    
    public final int elementCount() { return elements.size(); }
    public final Stream<IElement> getElements() { return elements.stream(); }
    
    public final IElement getElement(int index){ return elements.get(index); }
    public final IElement removeElement(int index){ return elements.remove(index); }
    
    public final IElement getHover(Vector mPos){
        for(int i = 0; i < elements.size(); i++){
            IElement cHover = elements.get(i).getHover(mPos.inverseTransform(getTransform(i))); 
            if(cHover != null){ return cHover; }
        }
        return super.getHover(mPos);
    }
    
    protected final void childRender(VGraphics g) {
        for(int i = 0; i < elements.size(); i++){
            g.save();
            g.transform(getTransform(i));
            elements.get(i).render(g);
            g.reset();
        }
    }
}

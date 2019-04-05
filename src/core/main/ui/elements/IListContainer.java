
package core.main.ui.elements;

import core.main.ui.active.IListTransformable;
import java.awt.geom.AffineTransform;
import java.util.stream.Stream;

public interface IListContainer extends IElement{
    
    public void addTransformHandler(IListTransformable transformable);
    
    public void removeTransformHandler(IListTransformable transformable);
    
    public void addElement(IElement e);
    
    public int elementCount();
    public Stream<IElement> getElements();
    public IElement getElement(int index);
    public IElement removeElement(int index);
    public AffineTransform getTransform(int index);
    
}

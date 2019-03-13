
package core.main.ui.elements;

import java.awt.geom.AffineTransform;
import java.util.Iterator;

public interface IListContainer extends IElement{
    
    public void addElement(IElement e);
    
    public int elementCount();
    public Iterator<IElement> getElements();
    public IElement getElement(int index);
    public IElement removeElement(int index);
    public AffineTransform getTransform(int index);
    
}

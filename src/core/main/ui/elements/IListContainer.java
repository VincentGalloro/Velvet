
package core.main.ui.elements;

import java.awt.geom.AffineTransform;
import java.util.Iterator;

public interface IListContainer {
    
    public void addElement(IElement e);
    
    public int getElementCount();
    public Iterator<IElement> getElements();
    public AffineTransform getTransform(int index);
    
}

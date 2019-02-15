
package core.main.ui;

import java.awt.geom.AffineTransform;
import java.util.Iterator;

public interface IListContainer extends IElement{
    
    public void addElement(IElement e);
    
    public int getElementCount();
    public Iterator<IElement> getElements();
    public AffineTransform getTransform(int index);
    
}

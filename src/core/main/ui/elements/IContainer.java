
package core.main.ui.elements;

import java.awt.geom.AffineTransform;

public interface IContainer extends IElement{
    
    public void setElement(IElement e);
    
    public IElement getElement();
    public AffineTransform getTransform();
}

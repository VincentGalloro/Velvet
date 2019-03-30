
package core.main.ui.elements;

import core.main.ui.active.IRenderable;
import java.awt.geom.AffineTransform;

public interface IContainer extends IElement{
    
    public void addPreChildRenderHandler(IRenderable rendereable);
    public void addPostChildRenderHandler(IRenderable rendereable);
    
    public void setElement(IElement e);
    
    public IElement getElement();
    public AffineTransform getTransform();
}

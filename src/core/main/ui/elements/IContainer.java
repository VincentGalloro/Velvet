
package core.main.ui.elements;

import core.main.ui.active.IRenderable;
import java.awt.geom.AffineTransform;

public interface IContainer extends IElement{
    
    public void addPreChildRenderHandler(IRenderable rendereable);
    public void addPostChildRenderHandler(IRenderable rendereable);
    
    public void removePreChildRenderHandler(IRenderable rendereable);
    public void removePostChildRenderHandler(IRenderable rendereable);
    
    public void setStrict(boolean strict);
    public boolean isStrict();
    
    public void setElement(IElement e);
    
    public IElement getElement();
    public AffineTransform getTransform();
}

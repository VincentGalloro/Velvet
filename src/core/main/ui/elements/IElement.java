package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IClickable;
import core.main.ui.active.IHoverable;

public interface IElement extends IClickable, IHoverable{
    
    public void addClickHandler(IClickable clickable);
    public void addHoverHandler(IHoverable hoverable);
    
    public Vector getSize();
    public IElement getHover(Vector mPos);
    
    public void render(VGraphics g);
}
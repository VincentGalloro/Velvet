package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IActivateable;
import core.main.ui.active.IUpdateable;

public interface IElement extends IUpdateable{
    
    public void addClickHandler(IActivateable clickable);
    public void addHoverHandler(IActivateable hoverable);
    public void addUpdateHandler(IUpdateable updateable);
    
    public void onHoverStart();
    public void onHoverEnd();
    public void onMousePress();
    public void onMouseRelease();
    
    public Vector getSize();
    public IElement getHover(Vector mPos);
    public String getName();
    
    public void render(VGraphics g);
}
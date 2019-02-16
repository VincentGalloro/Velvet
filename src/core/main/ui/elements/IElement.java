package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IClickable;
import core.main.ui.active.IHoverable;
import java.awt.geom.AffineTransform;

public interface IElement{
    
    public void addClickHandler(IClickable clickable);
    public void addHoverHandler(IHoverable hoverable);
    
    public void update(AffineTransform at);
    public boolean handleClick();
    
    public Vector getSize();
    
    public void render(VGraphics g);
}
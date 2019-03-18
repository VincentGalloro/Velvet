package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IEventable;
import core.main.ui.active.IRenderable;
import core.main.ui.active.IScrollEventable;
import core.main.ui.active.IUpdateable;
import java.awt.geom.AffineTransform;

public interface IElement extends IUpdateable, IRenderable{
    
    public void addUpdateHandler(IUpdateable updateable);
    public void addRenderHandler(IRenderable rendereable);
    public void addMousePressHandler(IEventable eventable);
    public void addMouseReleaseHandler(IEventable eventable);
    public void addMouseScrollHandler(IScrollEventable eventable);
    public void addHoverStartHandler(IEventable eventable);
    public void addHoverEndHandler(IEventable eventable);
    
    public void onMousePress();
    public void onMouseRelease();
    public void onMouseScroll(int amount);
    public void onHoverStart();
    public void onHoverEnd();
    
    public void update(AffineTransform at);
    
    public Vector getSize();
    public IElement getHover(Vector mPos);
    public String getName();
    
    public void render(VGraphics g);
}
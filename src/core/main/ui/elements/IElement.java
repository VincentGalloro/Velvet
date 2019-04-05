package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IEventable;
import core.main.ui.active.IKeyEventable;
import core.main.ui.active.IRenderable;
import core.main.ui.active.IScrollEventable;
import core.main.ui.active.IUpdateable;
import java.awt.geom.AffineTransform;

public interface IElement{
    
    public IElementBuilder getBuilder();
    
    public void addUpdateHandler(IUpdateable updateable);
    public void addPreRenderHandler(IRenderable rendereable);
    public void addPostRenderHandler(IRenderable rendereable);
    public void addMousePressHandler(IEventable eventable);
    public void addMouseReleaseHandler(IEventable eventable);
    public void addMouseScrollHandler(IScrollEventable eventable);
    public void addKeyPressedHandler(IKeyEventable eventable);
    public void addCharTypedHandler(IKeyEventable eventable);
    public void addHoverStartHandler(IEventable eventable);
    public void addHoverEndHandler(IEventable eventable);
    public void addFocusStartHandler(IEventable eventable);
    public void addFocusEndHandler(IEventable eventable);
    
    public void removeUpdateHandler(IUpdateable updateable);
    public void removePreRenderHandler(IRenderable rendereable);
    public void removePostRenderHandler(IRenderable rendereable);
    public void removeMousePressHandler(IEventable eventable);
    public void removeMouseReleaseHandler(IEventable eventable);
    public void removeMouseScrollHandler(IScrollEventable eventable);
    public void removeKeyPressedHandler(IKeyEventable eventable);
    public void removeCharTypedHandler(IKeyEventable eventable);
    public void removeHoverStartHandler(IEventable eventable);
    public void removeHoverEndHandler(IEventable eventable);
    public void removeFocusStartHandler(IEventable eventable);
    public void removeFocusEndHandler(IEventable eventable);
    
    public void onMousePress();
    public void onMouseRelease();
    public void onMouseScroll(int amount);
    public void onKeyPressed(int key);
    public void onCharTyped(int code);
    public void onHoverStart();
    public void onHoverEnd();
    public void onFocusStart();
    public void onFocusEnd();
    
    public void update(AffineTransform at);
    
    public Vector getSize();
    public IElement getHover(Vector mPos);
    public String getName();
    
    public void render(VGraphics g);
}
package core.main.ui;

import core.main.Mouse;
import core.main.VGraphics;
import core.main.ui.elements.IElement;

public class UIHandler {
    
    private Mouse mouse;
    private IElement root, currentHover;
    
    public UIHandler(IElement root, Mouse mouse){
        this.root = root;
        this.mouse = mouse;
    }
    
    public void update(){
        if(mouse.isReleased(Mouse.LEFT) && currentHover != null){
            currentHover.onMouseRelease();
        }
        
        IElement newHover = root.getHover(mouse.getPos());
        if(currentHover != newHover && !mouse.isDown(Mouse.LEFT)){
            if(currentHover != null){ currentHover.onHoverEnd(); }
            currentHover = newHover;
            if(currentHover != null){ currentHover.onHoverStart(); }
        }
        
        if(mouse.isPressed(Mouse.LEFT) && currentHover != null){
            currentHover.onMousePress();
        }
    }
    
    public void render(VGraphics g){
        root.render(g);
    }
}
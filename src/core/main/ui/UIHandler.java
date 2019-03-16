package core.main.ui;

import core.main.Mouse;
import core.main.VGraphics;
import core.main.ui.elements.IElement;
import java.awt.geom.AffineTransform;
import java.io.File;

public class UIHandler {
    
    private Mouse mouse;
    private IElement root, currentHover;
    
    public UIHandler(Mouse mouse){
        this.mouse = mouse;
    }
    
    public void setRoot(IElement root){
        this.root = root;
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
        
        if(mouse.getScrollAmount() != 0 && currentHover != null){
            currentHover.onMouseScroll(mouse.getScrollAmount());
        }
        
        root.update(new AffineTransform());
    }
    
    public UIController loadController(File file){
        return UIController.Factory.fromFile(file, mouse);
    }
    public UIController loadController(String fName){
        return loadController(new File(fName));
    }
    
    public void render(VGraphics g){
        root.render(g);
    }
}
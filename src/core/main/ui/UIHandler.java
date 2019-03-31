package core.main.ui;

import core.main.Mouse;
import core.main.VGraphics;
import core.main.ui.elements.ElementBuilderFactory;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IElementBuilderFactory;
import java.awt.geom.AffineTransform;
import java.io.File;

public class UIHandler {
    
    private IElementBuilderFactory elementBuilderFactory;
    private Mouse mouse;
    private IElement root, currentHover, currentFocus;
    
    public UIHandler(Mouse mouse){
        this.mouse = mouse;
        this.elementBuilderFactory = new ElementBuilderFactory();
    }
    
    public void setRoot(IElement root){ this.root = root; }
    public void setRoot(UIController uiController){ this.root = uiController.getRoot(); }
  
    public void setElementBuilderFactory(IElementBuilderFactory ebf){
        this.elementBuilderFactory = ebf;
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
        
        if(mouse.isPressed(Mouse.LEFT)){
            if(currentHover != null){
                currentHover.onMousePress();
            }
            
            if(currentHover != currentFocus){
                if(currentFocus != null){ currentFocus.onFocusEnd(); }
                currentFocus = currentHover;
                if(currentFocus != null){ currentFocus.onFocusStart(); }
            }
        }
        
        if(mouse.getScrollAmount() != 0 && currentHover != null){
            currentHover.onMouseScroll(mouse.getScrollAmount());
        }
        
        root.update(new AffineTransform());
    }
    
    public UIController loadController(File file){
        return UIController.Factory.fromFile(file, mouse, elementBuilderFactory);
    }
    public UIController loadController(String fName){
        return loadController(new File(fName));
    }
    
    public void render(VGraphics g){
        root.render(g);
    }
}
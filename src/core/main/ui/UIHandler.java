package core.main.ui;

import core.main.Keyboard;
import core.main.Mouse;
import core.main.VGraphics;
import core.main.ui.elements.ElementFactory;
import core.main.ui.elements.IElement;
import java.awt.geom.AffineTransform;
import java.io.File;
import core.main.ui.elements.IElementFactory;

public class UIHandler {
    
    private IElementFactory elementFactory;
    private final Mouse mouse;
    private final Keyboard keyboard;
    private IElement root, currentHover, currentFocus;
    
    public UIHandler(Mouse mouse, Keyboard keyboard){
        this.mouse = mouse;
        this.keyboard = keyboard;
        this.elementFactory = new ElementFactory();
    }
    
    public void setRoot(IElement root){ this.root = root; }
    public void setRoot(UIController uiController){ this.root = uiController.getRoot(); }
  
    public void setElementBuilderFactory(IElementFactory elementFactory){
        this.elementFactory = elementFactory;
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
        
        if(currentFocus != null){
            for(char c : keyboard.getTextTyped().toCharArray()){
                currentFocus.onCharTyped(c);
            }
            for(Integer i : keyboard.getPressedLog()){
                currentFocus.onKeyPressed(i);
            }
        }
        
        root.update(new AffineTransform());
    }
    
    public UIController loadController(File file){
        return UIController.Factory.fromFile(file, mouse, elementFactory);
    }
    public UIController loadController(String fName){
        return loadController(new File(fName));
    }
    
    public void render(VGraphics g){
        root.render(g);
    }
}
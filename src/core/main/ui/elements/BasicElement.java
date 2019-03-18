
package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IEventable;
import core.main.ui.active.IRenderable;
import core.main.ui.active.IScrollEventable;
import java.util.ArrayList;
import core.main.ui.active.IUpdateable;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public abstract class BasicElement implements IElement{

    public static abstract class Builder implements ElementBuilder{

        private final BasicElement element;
        
        public Builder(BasicElement element){ this.element = element; }
        
        public void handleString(String field, String value) {
            if(field.equals("name")){ element.name = value; }
        }

        public final IElement get() { return element; }
        
        public final Color toColor(String s){
            String[] tokens = s.split(" ");
            return new Color(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
        }
    }
    
    private String name;
    private final ArrayList<IUpdateable> updateHandlers;
    private final ArrayList<IRenderable> renderHandlers;
    private final ArrayList<IEventable> mousePressHandlers, mouseReleaseHandlers, 
            hoverStartHandlers, hoverEndHandlers;
    private final ArrayList<IScrollEventable> mouseScrollHandlers;
    
    public BasicElement(){
        updateHandlers = new ArrayList<>();
        renderHandlers = new ArrayList<>();
        mousePressHandlers = new ArrayList<>();
        mouseReleaseHandlers = new ArrayList<>();
        hoverStartHandlers = new ArrayList<>();
        hoverEndHandlers = new ArrayList<>();
        mouseScrollHandlers = new ArrayList<>();
    }
    
    public void addUpdateHandler(IUpdateable updateable){ updateHandlers.add(updateable); }  
    public void addRenderHandler(IRenderable rendereable){ renderHandlers.add(rendereable); }  
    public void addMousePressHandler(IEventable eventable){ mousePressHandlers.add(eventable); }
    public void addMouseReleaseHandler(IEventable eventable){ mouseReleaseHandlers.add(eventable); }
    public void addMouseScrollHandler(IScrollEventable eventable){ mouseScrollHandlers.add(eventable); }  
    public void addHoverStartHandler(IEventable eventable){ hoverStartHandlers.add(eventable); }
    public void addHoverEndHandler(IEventable eventable){ hoverEndHandlers.add(eventable); }
    
    public final void onMousePress(){ for(IEventable e : mousePressHandlers){ e.onEvent(); } }
    public final void onMouseRelease(){ for(IEventable e : mouseReleaseHandlers){ e.onEvent(); } }
    public final void onMouseScroll(int amount){ for(IScrollEventable e : mouseScrollHandlers){ e.onScroll(amount); } }
    public final void onHoverStart(){ for(IEventable e : hoverStartHandlers){ e.onEvent(); } }
    public final void onHoverEnd(){ for(IEventable e : hoverEndHandlers){ e.onEvent(); } }
    
    public final void update(AffineTransform at){
        for(IUpdateable u : updateHandlers){ u.update(at); }
        containerUpdate(at);
    }
    protected void containerUpdate(AffineTransform at){}
    
    public IElement getHover(Vector mPos){
        if(isHovered(mPos)){ return this; }
        return null;
    }
    public String getName(){ return name; }
    
    public boolean isHovered(Vector mPos){
        if(mousePressHandlers.isEmpty() && mouseReleaseHandlers.isEmpty() && 
                hoverStartHandlers.isEmpty() && hoverEndHandlers.isEmpty() &&
                mouseScrollHandlers.isEmpty()){ return false; }
        return mPos.greaterThan(new Vector()) && mPos.lessThan(getSize());
    }
    
    public final void render(VGraphics g){
        onRender(g);
        for(IRenderable r : renderHandlers){ r.render(g); }
    }
    
    public abstract void onRender(VGraphics g);
}

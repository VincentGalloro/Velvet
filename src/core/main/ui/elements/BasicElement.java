
package core.main.ui.elements;

import core.main.structs.Vector;
import java.util.ArrayList;
import core.main.ui.active.IClickable;
import core.main.ui.active.IHoverable;
import java.awt.Color;

public abstract class BasicElement implements IElement{

    public static class Builder implements ElementBuilder{

        private final BasicElement element;
        
        public Builder(BasicElement element){ this.element = element; }
        
        public void handleString(String field, String value) {
            //TODO
        }

        public final IElement create() { return element; }
        
        public final Color toColor(String s){
            return Color.getColor(s);
        }
    }
    
    private String name;
    private ArrayList<IClickable> clickables;
    private ArrayList<IHoverable> hoverables;
    
    public BasicElement(){
        clickables = new ArrayList<>();
        hoverables = new ArrayList<>();
    }
    
    public final void addClickHandler(IClickable clickable){ clickables.add(clickable); }
    public final void addHoverHandler(IHoverable hoverable){ hoverables.add(hoverable); }
    
    public final void onHoverStart(){ for(IHoverable h : hoverables){ h.onHoverStart(); } }
    public final void onHoverEnd(){ for(IHoverable h : hoverables){ h.onHoverEnd(); } }
    public final void onMousePress(){ for(IClickable c : clickables){ c.onMousePress(); } }
    public final void onMouseRelease(){ for(IClickable c : clickables){ c.onMouseRelease(); } }
    
    public IElement getHover(Vector mPos){
        if(isHovered(mPos)){ return this; }
        return null;
    }
    public String getName(){ return name; }
    
    public boolean isHovered(Vector mPos){
        if(clickables.isEmpty() && hoverables.isEmpty()){ return false; }
        return mPos.greaterThan(new Vector()) && mPos.lessThan(getSize());
    }
}


package core.main.ui.elements;

import core.main.structs.Vector;
import core.main.ui.active.IActivateable;
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
    private ArrayList<IActivateable> clickables, hoverables;
    private ArrayList<IUpdateable> updateables;
    
    public BasicElement(){
        clickables = new ArrayList<>();
        hoverables = new ArrayList<>();
        updateables = new ArrayList<>();
    }
    
    public final void addClickHandler(IActivateable clickable){ clickables.add(clickable); }
    public final void addHoverHandler(IActivateable hoverable){ hoverables.add(hoverable); }
    public final void addUpdateHandler(IUpdateable updateable){ updateables.add(updateable); }
    
    public final void onHoverStart(){ for(IActivateable h : hoverables){ h.onStart(); } }
    public final void onHoverEnd(){ for(IActivateable h : hoverables){ h.onStop(); } }
    public final void onMousePress(){ for(IActivateable c : clickables){ c.onStart(); } }
    public final void onMouseRelease(){ for(IActivateable c : clickables){ c.onStop(); } }
    
    public final void update(AffineTransform at){
        for(IUpdateable u : updateables){ u.update(at); }
        containerUpdate(at);
    }
    protected void containerUpdate(AffineTransform at){}
    
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

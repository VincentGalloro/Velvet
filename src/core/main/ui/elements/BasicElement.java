
package core.main.ui.elements;

import core.main.structs.Vector;
import core.main.ui.active.IClickable;
import core.main.ui.active.IHoverable;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public abstract class BasicElement implements IElement{

    protected ArrayList<IClickable> clickables;
    protected ArrayList<IHoverable> hoverables;
    protected boolean isHovered;
    
    public BasicElement(){
        clickables = new ArrayList<>();
        hoverables = new ArrayList<>();
    }
    
    public final void addClickHandler(IClickable clickable){ clickables.add(clickable); }
    public final void addHoverHandler(IHoverable hoverable){ hoverables.add(hoverable); }
    
    public final void update(AffineTransform at){
        containerUpdate();
    }
    public void containerUpdate(){}
    
    public final boolean handleClick(){
        for(IClickable c : clickables){ c.onClick(); }
        return !clickables.isEmpty();
    }
}

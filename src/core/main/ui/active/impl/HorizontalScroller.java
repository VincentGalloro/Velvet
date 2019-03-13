package core.main.ui.active.impl;

import core.main.Mouse;
import core.main.structs.Vector;
import core.main.ui.active.IActivateable;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.IScrollable;
import java.awt.geom.AffineTransform;

public class HorizontalScroller implements IActivateable, IUpdateable{

    private Mouse mouse;
    private IScrollable scrollable;
    private boolean dragging;
    
    public HorizontalScroller(IScrollable scrollable, Mouse mouse){
        this.scrollable = scrollable;
        this.mouse = mouse;
    }
    
    public void onStart() {
        dragging = true;
    }

    public void onStop() {
        dragging = false;
    }

    public void update(AffineTransform at) {
        if(dragging){
            Vector mPos = mouse.getPos().inverseTransform(at);
            double delta = (mPos.x - scrollable.getOffset()) / scrollable.getLength();
            scrollable.setDelta(Math.min(Math.max(delta, 0), 1));
        }
    }
}
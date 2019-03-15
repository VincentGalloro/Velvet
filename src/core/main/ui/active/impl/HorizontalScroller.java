package core.main.ui.active.impl;

import core.main.Mouse;
import core.main.structs.Vector;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.IScrollable;
import java.awt.geom.AffineTransform;

public class HorizontalScroller implements IUpdateable{

    private final Mouse mouse;
    private final IScrollable scrollable;
    private final DragObserver dragObserver;
    
    public HorizontalScroller(IScrollable scrollable, Mouse mouse, DragObserver dragObserver){
        this.scrollable = scrollable;
        this.mouse = mouse;
        this.dragObserver = dragObserver;
    }

    public void update(AffineTransform at) {
        if(dragObserver.isDragging()){
            Vector mPos = mouse.getPos().inverseTransform(at);
            double delta = (mPos.x - scrollable.getOffset()) / scrollable.getLength();
            scrollable.setDelta(Math.min(Math.max(delta, 0), 1));
        }
    }
}
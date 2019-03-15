package core.main.ui.active.impl;

import core.main.ui.active.IEventable;
import core.main.ui.elements.IElement;

public class DragObserver{

    private boolean dragging;
    
    public DragObserver(IElement target){
        target.addMousePressHandler(new DragSetter(true));
        target.addMouseReleaseHandler(new DragSetter(false));
    }
    
    private class DragSetter implements IEventable{
        private boolean target;
        
        public DragSetter(boolean target){ this.target = target; }
        
        public void onEvent(){ dragging = target; }
    }
    
    public boolean isDragging(){ return dragging; }
}
package core.main.ui.active.impl;

import core.main.ui.elements.IElement;

public class DragObserver{

    private boolean dragging;
    
    public DragObserver(IElement target){
        target.addMousePressHandler(() -> { dragging = true; });
        target.addMouseReleaseHandler(() -> { dragging = false; });
    }
    
    public boolean isDragging(){ return dragging; }
}
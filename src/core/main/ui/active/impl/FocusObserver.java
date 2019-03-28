package core.main.ui.active.impl;

import core.main.ui.elements.IElement;

public class FocusObserver{

    private boolean focus;
    
    public FocusObserver(IElement target){
        target.addFocusStartHandler(() -> { focus = true; });
        target.addFocusEndHandler(() -> { focus = false; });
    }
    
    public boolean isFocussed(){ return focus; }
}
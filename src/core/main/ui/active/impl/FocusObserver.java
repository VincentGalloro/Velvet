package core.main.ui.active.impl;

import core.main.ui.active.IEventable;
import core.main.ui.elements.IElement;

public class FocusObserver{

    private boolean focus;
    
    public FocusObserver(IElement target){
        target.addFocusStartHandler(new FocusSetter(true));
        target.addFocusEndHandler(new FocusSetter(false));
    }
    
    private class FocusSetter implements IEventable{
        private final boolean target;
        
        public FocusSetter(boolean target){ this.target = target; }
        
        public void onEvent(){ focus = target; }
    }
    
    public boolean isFocussed(){ return focus; }
}
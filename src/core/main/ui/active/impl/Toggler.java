package core.main.ui.active.impl;

import core.main.ui.active.IActivateable;
import core.main.ui.elements.IToggleable;

public class Toggler implements IActivateable{

    private final IToggleable toggleable;
    
    public Toggler(IToggleable toggleable){
        this.toggleable = toggleable;
    }
    
    public void onStart() {
        toggleable.toggle();
    }

    public void onStop() {}
}
package core.main.ui.active.impl;

import core.main.ui.active.IActivateable;
import core.main.ui.elements.IToggleable;

public class ToggleConditional implements IActivateable{

    private IToggleable toggleable;
    private IActivateable offActivateable, onActivateable;
    
    public ToggleConditional(IToggleable toggleable, IActivateable offActivateable, IActivateable onActivateable){
        this.toggleable = toggleable;
        this.offActivateable = offActivateable;
        this.onActivateable = onActivateable;
    }
    
    public void onStart() {
        if(toggleable.isToggled()){
            if(onActivateable != null){ onActivateable.onStart(); }
        }
        else{
            if(offActivateable != null){ offActivateable.onStart(); }
        }
    }

    public void onStop() {
        if(toggleable.isToggled()){
            if(onActivateable != null){ onActivateable.onStop();}
        }
        else{
            if(offActivateable != null){ offActivateable.onStop();}
        }
    }
}
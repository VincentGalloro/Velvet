package core.main.ui.elements;

import core.main.ui.active.IActivateable;
import java.util.ArrayList;

public abstract class BasicToggleable extends BasicElement implements IToggleable{

    protected boolean toggled;
    private ArrayList<IActivateable> toggleables;
    
    public BasicToggleable(){
        toggleables = new ArrayList<>();
    }
    
    public final void addToggleHandler(IActivateable toggleable) { toggleables.add(toggleable); }
    
    public final void toggle() { setToggle(!toggled); }
    public final void setToggle(boolean t) { 
        if(toggled == t){ return; }
        toggled = t;
        if(toggled){ onToggleOn(); }
        else{ onToggleOff(); }
    }

    public final void onToggleOn() { for(IActivateable t : toggleables){ t.onStart(); } }
    public final void onToggleOff() { for(IActivateable t : toggleables){ t.onStop(); } }
    public final boolean isToggled() { return toggled; }
}
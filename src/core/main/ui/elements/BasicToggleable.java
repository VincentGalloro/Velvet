package core.main.ui.elements;

import core.main.ui.active.IActivateable;
import java.util.ArrayList;

public abstract class BasicToggleable extends BasicElement implements IToggleable{

    public static abstract class Builder extends BasicElement.Builder{
        
        private final BasicToggleable toggleable;
        
        public Builder(BasicToggleable toggleable) {
            super(toggleable);
            this.toggleable = toggleable;
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            //TODO
        }
    }

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
package core.main.ui.elements;

import core.main.ui.active.IEventable;
import java.util.ArrayList;

public abstract class BasicToggleable extends BasicElement implements IToggleable{

    public class Builder extends BasicElement.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            //TODO
        }
    }

    protected boolean toggled;
    private final ArrayList<IEventable> toggleHandlers, toggleOnHandlers, toggleOffHandlers;
    
    public BasicToggleable(){
        toggleHandlers = new ArrayList<>();
        toggleOnHandlers = new ArrayList<>();
        toggleOffHandlers = new ArrayList<>();
    }
    
    public final void addToggleHandler(IEventable eventable){ toggleHandlers.add(eventable); }
    public final void addToggleOnHandler(IEventable eventable){ toggleOnHandlers.add(eventable); }
    public final void addToggleOffHandler(IEventable eventable){ toggleOffHandlers.add(eventable); }
    
    public final void toggle() { setToggle(!toggled); }
    public final void setToggle(boolean t) { 
        if(toggled == t){ return; }
        toggled = t;
        if(toggled){ onToggleOn(); }
        else{ onToggleOff(); }
        
        for(IEventable e : toggleHandlers){ e.onEvent(); }
    }

    public final void onToggleOn() { for(IEventable e : toggleOnHandlers){ e.onEvent(); } }
    public final void onToggleOff() { for(IEventable e : toggleOffHandlers){ e.onEvent(); } }
    
    public final boolean isToggled() { return toggled; }
}
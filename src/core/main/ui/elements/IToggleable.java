package core.main.ui.elements;

import core.main.ui.active.IActivateable;

public interface IToggleable {

    public void addToggleHandler(IActivateable toggleable);
    
    public void toggle();
    public void setToggle(boolean t);
    
    public void onToggleOn();
    public void onToggleOff();
    
    public boolean isToggled();
}
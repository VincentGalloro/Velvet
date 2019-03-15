package core.main.ui.elements;

import core.main.ui.active.IEventable;

public interface IToggleable extends IElement{

    public void addToggleHandler(IEventable eventable);
    public void addToggleOnHandler(IEventable eventable);
    public void addToggleOffHandler(IEventable eventable);
    
    public void toggle();
    public void setToggle(boolean t);
    
    public void onToggleOn();
    public void onToggleOff();
    
    public boolean isToggled();
}
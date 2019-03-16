package core.main.ui.active.impl;

import core.main.ui.active.IScrollEventable;
import core.main.ui.elements.IScrollable;

public class WheelScroller implements IScrollEventable{

    private IScrollable scrollable;
    private double scrollFactor;
    
    public WheelScroller(IScrollable scrollable, double scrollFactor){
        this.scrollable = scrollable;
        this.scrollFactor = scrollFactor;
    }
    
    public WheelScroller(IScrollable scrollable, double scrollFactor, boolean inverted){
        this(scrollable, scrollFactor * (inverted ? -1 : 1));
    }
    
    public void onScroll(int amount) {
        scrollable.setDelta(scrollable.getDelta() + amount*scrollFactor);
    }
}
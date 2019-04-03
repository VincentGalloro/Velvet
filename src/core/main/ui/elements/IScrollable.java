
package core.main.ui.elements;

import core.main.ui.active.IDeltable;

public interface IScrollable extends IElement{
    
    public void addDeltaHandler(IDeltable deltaHandler);
    
    public void setDelta(double d);
    public void setLength(double l);
    public void setScrollablePercentage(double d);
    
    public double getDelta();
    public double getLength();
    public double getScrollablePercentage();
}

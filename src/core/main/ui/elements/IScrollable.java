
package core.main.ui.elements;

public interface IScrollable extends IElement{
    
    public void setDelta(double d);
    
    public double getDelta();
    public double getLength();
    public double getOffset();
}

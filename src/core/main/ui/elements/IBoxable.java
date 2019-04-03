package core.main.ui.elements;

import java.awt.Color;

public interface IBoxable extends IElement{
    
    public void setOutlineColor(Color o);
    public void setFillColor(Color f);
    public void setOutlineThickness(float t);
    public void setRounding(Double d);
    
    public Color getOutlineColor();
    public Color getFillColor();
}
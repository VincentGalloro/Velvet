package core.main.ui.elements;

import java.awt.Color;
import java.awt.Shape;

public interface IShapeable extends IElement{
    
    public void setOutlineColor(Color o);
    public void setFillColor(Color f);
    public void setOutlineThickness(float t);
    
    public Shape getShape();
    public Color getOutlineColor();
    public Color getFillColor();
}
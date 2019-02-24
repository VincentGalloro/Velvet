package core.main.ui.elements;

import java.awt.Color;

public interface IBoxable extends IElement{

    public void setOutlineColor(Color o);
    public void setFillColor(Color f);
    public void setThickness(float t);
}
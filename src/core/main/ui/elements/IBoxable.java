package core.main.ui.elements;

import core.main.ui.active.IColorAdapter;
import java.awt.Color;

public interface IBoxable extends IElement{
    
    public void setOutlineColor(Color o);
    public void setFillColor(Color f);
    public void setThickness(float t);

    public IColorAdapter getOutlineColorAdapter();
}
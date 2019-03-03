package core.main.ui.elements;

import core.main.ui.active.adapters.impl.BoxOutlineAdapter;
import java.awt.Color;

public interface IBoxable extends IElement{
    
    public void setOutlineColor(Color o);
    public void setFillColor(Color f);
    public void setThickness(float t);

    public BoxOutlineAdapter getOutlineColorAdapter();
}
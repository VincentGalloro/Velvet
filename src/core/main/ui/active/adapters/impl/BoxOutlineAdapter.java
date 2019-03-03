package core.main.ui.active.adapters.impl;

import core.main.ui.active.adapters.IColorAdapter;
import core.main.ui.elements.IBoxable;
import java.awt.Color;

public class BoxOutlineAdapter implements IColorAdapter{

    private final IBoxable boxable;
    
    public BoxOutlineAdapter(IBoxable boxable){
        this.boxable = boxable;
    }
    
    public void setColor(Color c) {
        boxable.setOutlineColor(c);
    }
}
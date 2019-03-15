package core.main.ui.active.impl;

import core.main.ui.active.IEventable;
import core.main.ui.active.adapters.IColorAdapter;
import java.awt.Color;

public class ColorTransition implements IEventable{

    private final IColorAdapter colorAdapter;
    private final Color color;
    
    public ColorTransition(IColorAdapter colorAdapter, Color color){
        this.colorAdapter = colorAdapter;
        this.color = color;
    }
    
    public void onEvent() { colorAdapter.setColor(color); }
}
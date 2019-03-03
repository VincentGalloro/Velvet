package core.main.ui.active.adapters.impl;

import core.main.ui.active.adapters.IColorAdapter;
import java.awt.Color;

public class SmoothSetterAdapter implements IColorAdapter{

    private final SmoothColorAdapter colorAdapter;
    
    public SmoothSetterAdapter(SmoothColorAdapter colorAdapter){
        this.colorAdapter = colorAdapter;
    }
    
    public void setColor(Color c) {
        colorAdapter.setSmooth(c);
    }
}
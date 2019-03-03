package core.main.ui.active.impl;

import core.main.ui.active.IActivateable;
import core.main.ui.active.adapters.IColorAdapter;
import java.awt.Color;

public class ColorTransition implements IActivateable{

    private final IColorAdapter colorAdapter;
    private final Color startColor, stopColor;
    
    public ColorTransition(IColorAdapter colorAdapter, Color startColor, Color stopColor){
        this.colorAdapter = colorAdapter;
        this.startColor = startColor;
        this.stopColor = stopColor;
    }
    
    public void onStart() {
        if(startColor != null){
            colorAdapter.setColor(startColor);
        }
    }

    public void onStop() {
        if(stopColor != null){
            colorAdapter.setColor(stopColor);
        }
    }
}
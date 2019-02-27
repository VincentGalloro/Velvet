package core.main.ui.active.impl;

import core.main.ui.active.IActivateable;
import java.awt.Color;

public class SmoothColorSetter implements IActivateable{

    private final SmoothColorAdapter colorAdapter;
    private final Color startColor, stopColor;
    
    public SmoothColorSetter(SmoothColorAdapter colorAdapter, Color startColor, Color stopColor){
        this.colorAdapter = colorAdapter;
        this.startColor = startColor;
        this.stopColor = stopColor;
    }
    
    public void onStart() {
        if(startColor != null){
            colorAdapter.setSmooth(startColor);
        }
    }

    public void onStop() {
        if(stopColor != null){
            colorAdapter.setSmooth(stopColor);
        }
    }
}
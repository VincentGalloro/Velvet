package core.main.ui.active.impl;

import core.main.ui.active.IColorAdapter;
import core.main.smooth.SmoothColor;
import core.main.ui.active.IColorSetterAdapter;
import core.main.ui.active.IUpdateable;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class SmoothColorAdapter implements IColorAdapter, IUpdateable {

    private final IColorSetterAdapter adapter;
    private final SmoothColor color;
    
    public SmoothColorAdapter(IColorSetterAdapter adapter, SmoothColor color){
        this.adapter = adapter;
        this.color = color;
    }
    
    public Color getColor(){ return color.getColor(); }
    
    public IColorAdapter getSmoothSetterAdapter(){
        return new IColorAdapter(){
            public Color getColor(){ return color.getSmooth(); }
            public void setColor(Color c){ color.setSmooth(c); }
        };
    }
    
    public void setColor(Color c) { color.setColor(c); }
    
    public void update(AffineTransform at) {
        color.update();
        adapter.setColor(color.getSmooth());
    }
}
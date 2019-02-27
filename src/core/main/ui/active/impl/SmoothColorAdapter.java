package core.main.ui.active.impl;

import core.main.ui.active.IColorAdapter;
import core.main.smooth.SmoothColor;
import core.main.ui.active.IUpdateable;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class SmoothColorAdapter implements IColorAdapter, IUpdateable {

    private IColorAdapter adapter;
    private SmoothColor color;
    
    public SmoothColorAdapter(IColorAdapter adapter, SmoothColor color){
        this.adapter = adapter;
        this.color = color;
    }
    
    public void setColor(Color c) {
        color.setColor(c);
    }
    public void setSmooth(Color c){
        color.setSmooth(c);
    }
    
    public void update(AffineTransform at) {
        color.update();
        adapter.setColor(color.getSmooth());
    }
}
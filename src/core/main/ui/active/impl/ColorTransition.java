package core.main.ui.active.impl;

import core.main.ui.active.IColorGetterAdapter;
import core.main.ui.active.IEventable;
import core.main.ui.active.IColorSetterAdapter;
import java.awt.Color;

public class ColorTransition implements IEventable{

    private final IColorSetterAdapter colorSetterAdapter;
    private final IColorGetterAdapter colorGetterAdapter;
    
    public ColorTransition(IColorSetterAdapter colorSetterAdapter, IColorGetterAdapter colorGetterAdapter){
        this.colorGetterAdapter = colorGetterAdapter;
        this.colorSetterAdapter = colorSetterAdapter;
    }
    
    public ColorTransition(IColorSetterAdapter colorSetterAdapter, Color c){
        this(colorSetterAdapter, new IColorGetterAdapter(){ public Color getColor(){ return c; } } );
    }
    
    public void onEvent() { colorSetterAdapter.setColor(colorGetterAdapter.getColor()); }
}
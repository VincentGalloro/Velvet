package core.main.ui.active.adapters.impl;

import core.main.ui.active.adapters.IColorAdapter;
import java.awt.Color;
import java.util.ArrayList;

public class ColorMultiAdapter implements IColorAdapter{

    private ArrayList<IColorAdapter> adapters;
    
    public ColorMultiAdapter(){
        adapters = new ArrayList<>();
    }
    
    public void addAdapter(IColorAdapter adapter){ adapters.add(adapter); }
    
    public void setColor(Color c) {
        for(IColorAdapter adapter : adapters){ adapter.setColor(c); }
    }
}
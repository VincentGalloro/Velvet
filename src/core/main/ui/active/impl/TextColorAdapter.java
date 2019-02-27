package core.main.ui.active.impl;

import core.main.ui.active.IColorAdapter;
import core.main.ui.elements.ITextable;
import java.awt.Color;

public class TextColorAdapter implements IColorAdapter{

    private final ITextable textable;
    
    public TextColorAdapter(ITextable textable){
        this.textable = textable;
    }
    
    public void setColor(Color c) {
        textable.setTextColor(c);
    }
}
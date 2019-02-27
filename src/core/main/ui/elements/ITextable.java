package core.main.ui.elements;

import core.main.ui.active.impl.TextColorAdapter;
import java.awt.Color;

public interface ITextable extends IElement{

    public void setText(String t);
    public void setTextColor(Color c);
    
    public TextColorAdapter getTextColorAdapter();
    public String getText();
    public Color getTextColor();
}
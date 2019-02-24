package core.main.ui.elements;

import java.awt.Color;

public interface ITextable {

    public void setText(String t);
    public void setTextColor(Color c);
    
    public String getText();
    public Color getTextColor();
}
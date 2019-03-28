package core.main.ui.elements;

import java.awt.Color;
import java.awt.geom.AffineTransform;

public interface ITextable extends IElement{

    public void setText(String t);
    public void setTextColor(Color c);
    
    public boolean supportsNewline();
    public String getText();
    public Color getTextColor();
    
    public AffineTransform getCharTransform(int charIndex);
}
package core.main.ui.elements;

import core.main.ui.active.IFontMetricsEventable;
import core.main.ui.active.ITextEventable;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;

public interface ITextable extends IElement{

    public void addTextChangeHandler(ITextEventable e);
    public void addFontMetricsChangeHandler(IFontMetricsEventable e);
    
    public void setText(String t);
    public void setTextColor(Color c);
    public void setFontMetrics(FontMetrics fm);
    
    public boolean supportsNewline();
    public String getText();
    public Color getTextColor();
    
    public AffineTransform getCharTransform(int charIndex);
}
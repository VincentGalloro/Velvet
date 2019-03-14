package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicTextable;

public class LabelElement extends BasicTextable{

    public static class Builder extends BasicTextable.Builder{
        public Builder() { super(new LabelElement()); }
    }
    
    public Vector getSize() { 
        return new Vector(fontMetrics.stringWidth(text), fontMetrics.getAscent()+fontMetrics.getDescent()); 
    }
    
    public void render(VGraphics g) {
        g.setColor(color);
        g.setFont(font);
        fontMetrics = g.getGraphics().getFontMetrics();
        g.drawString(text, new Vector(0, fontMetrics.getAscent()));
    }
}
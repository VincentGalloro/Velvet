package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicTextable;
import java.awt.geom.AffineTransform;

public class LabelElement extends BasicTextable{

    public static class Builder extends BasicTextable.Builder{
        public Builder() { super(new LabelElement()); }
    }
    
    public LabelElement(){
        addPostRenderHandler(this::postRender);
    }
    
    public boolean supportsNewline() { return false; }
    
    public Vector getSize() { 
        return new Vector(fontMetrics.stringWidth(text), fontMetrics.getAscent()+fontMetrics.getDescent()); 
    }
    
    public AffineTransform getCharTransform(int charIndex) {
        AffineTransform at = new AffineTransform();
        at.translate(fontMetrics.stringWidth(text.substring(0, charIndex)), fontMetrics.getAscent());
        return at;
    }
    
    public void postRender(VGraphics g) {
        super.onRender(g);
        g.drawString(text, new Vector(0, fontMetrics.getAscent()));
    }
}
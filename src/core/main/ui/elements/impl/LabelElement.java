package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicTextable;
import core.main.ui.elements.IElementBuilder;
import java.awt.geom.AffineTransform;

public class LabelElement extends BasicTextable{

    public LabelElement(){
        addPostRenderHandler(this::postRender);
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
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
        g.drawString(text, new Vector(0, fontMetrics.getAscent()));
    }
}
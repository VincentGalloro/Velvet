
package core.main.ui.active.impl;

import core.main.ui.active.IHoverable;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.ITextable;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class TextColorTransition implements IHoverable, IUpdateable{

    private final ITextable textElement;
    private final Color color, hover;
    
    public TextColorTransition(ITextable textElement, Color color, Color hover){
        this.textElement = textElement;
        this.color = color;
        this.hover = hover;
    }
    
    public void onHoverStart() {
        textElement.setTextColor(hover);
    }

    public void onHoverEnd() {
        textElement.setTextColor(color);
    }

    public void update(AffineTransform at) {
        
    }
}

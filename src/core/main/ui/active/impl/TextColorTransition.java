
package core.main.ui.active.impl;

import core.main.ui.active.IHoverable;
import core.main.ui.active.IUpdateable;
import core.main.ui.elements.impl.TextElement;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class TextColorTransition implements IHoverable, IUpdateable{

    private final TextElement textElement;
    private final Color color, hover;
    
    public TextColorTransition(TextElement textElement, Color color, Color hover){
        this.textElement = textElement;
        this.color = color;
        this.hover = hover;
    }
    
    public void onHoverStart() {
        textElement.setColor(hover);
    }

    public void onHoverEnd() {
        textElement.setColor(color);
    }

    public void update(AffineTransform at) {
        
    }
}

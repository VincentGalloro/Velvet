package core.main.ui.active.impl;

import core.main.ui.active.IClickable;
import core.main.ui.elements.ITextable;
import java.awt.Color;

public class TextClickColorTransition implements IClickable{

    private final ITextable textElement;
    private final Color click;
    
    public TextClickColorTransition(ITextable textElement, Color click){
        this.textElement = textElement;
        this.click = click;
    }
    
    public void onMousePress() {
        textElement.setTextSmoothColor(click);
    }

    public void onMouseRelease() {}
}
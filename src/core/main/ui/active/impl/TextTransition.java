package core.main.ui.active.impl;

import core.main.ui.active.IActivateable;
import core.main.ui.elements.ITextable;

public class TextTransition implements IActivateable{

    private final ITextable textable;
    private final String startText, stopText;
    
    public TextTransition(ITextable textable, String startText, String stopText){
        this.textable = textable;
        this.startText = startText;
        this.stopText = stopText;
    }
    
    public void onStart() {
        if(startText != null){
            textable.setText(startText);
        }
    }

    public void onStop() {
        if(stopText != null){
            textable.setText(stopText);
        }
    }
}
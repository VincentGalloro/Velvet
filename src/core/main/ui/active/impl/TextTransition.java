package core.main.ui.active.impl;

import core.main.ui.active.IEventable;
import core.main.ui.elements.ITextable;

public class TextTransition implements IEventable{

    private final ITextable textable;
    private final String text;
    
    public TextTransition(ITextable textable, String text){
        this.textable = textable;
        this.text = text;
    }
    
    public void onEvent() { textable.setText(text); }
}
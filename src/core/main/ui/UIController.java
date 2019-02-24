package core.main.ui;

import core.main.ui.elements.IElement;
import java.util.HashMap;

public class UIController{

    private IElement root;
    
    private HashMap<String, IElement> elements;
    
    public UIController(IElement root){
        this.root = root;
    }
    
    public void addElement(IElement e){ elements.put(e.getName(), e); }
    
    public IElement getElement(String name){ return elements.get(name); }
}

import core.main.Mouse;
import core.main.ui.elements.ElementFactory;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IElementFactory;

public class CustomElements implements IElementFactory{
    
    private ElementFactory ebf;
    
    public CustomElements(){
        ebf = new ElementFactory();
    }
    
    public IElement fromString(String name, Mouse mouse){
        if(name.equals("pulse line")){ return null; }
        
        return ebf.fromString(name, mouse);
    }
}

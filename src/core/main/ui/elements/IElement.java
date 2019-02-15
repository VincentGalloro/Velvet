package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;

public interface IElement {

    public Vector getSize();
    
    public void update();
    
    public void render(VGraphics g);
}
package core.main.ui;

import core.main.VGraphics;
import core.main.structs.Vector;

public interface IElement {

    public Vector getSize();
    
    public void render(VGraphics g);
}
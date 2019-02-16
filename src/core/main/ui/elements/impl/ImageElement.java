package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicElement;
import java.awt.image.BufferedImage;

public class ImageElement extends BasicElement{

    private BufferedImage image;
    
    public void setImage(BufferedImage i){ image = i; }
    
    public Vector getSize() {
        if(image==null){ return new Vector(); }
        return new Vector(image.getWidth(), image.getHeight());
    }

    public void render(VGraphics g) {
        g.drawImage(image, new Vector());
    }
}
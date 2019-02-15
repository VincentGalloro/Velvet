package core.main.ui;

import core.main.VGraphics;
import core.main.structs.Vector;
import java.awt.image.BufferedImage;

public class ImageElement implements IElement{

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
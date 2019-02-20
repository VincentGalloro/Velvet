package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicElement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageElement extends BasicElement{

    public static class Builder extends BasicElement.Builder{

        private final ImageElement image;
                
        public Builder() {
            super(new ImageElement());
            image = (ImageElement)create();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("path")){ 
                try {
                    image.image = ImageIO.read(new File(value));
                } catch (IOException ex) {}
            }
        }
    }
    
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
package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicElement;
import core.main.ui.elements.IElementBuilder;
import core.main.ui.elements.IImageable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageElement extends BasicElement implements IImageable{

    public class Builder extends BasicElement.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("path")){ 
                try {
                    image = ImageIO.read(new File(value));
                } catch (IOException ex) {
                    System.out.println("COULD NOT FIND IMAGE: "+value);
                }
            }
        }
    }
    
    private BufferedImage image;
    
    public ImageElement(){
        addPostRenderHandler(this::postRender);
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public void setImage(BufferedImage i){ image = i; }
    
    public Vector getSize() {
        if(image==null){ return new Vector(); }
        return new Vector(image.getWidth(), image.getHeight());
    }
    public BufferedImage getImage(){ return image; }

    public void postRender(VGraphics g) {
        g.drawImage(image, new Vector());
    }
}
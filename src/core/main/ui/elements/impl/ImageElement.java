package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicElement;
import core.main.ui.elements.IElementBuilder;
import core.main.ui.elements.IImageable;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
        if(image != null){
            AffineTransform at = g.getTransform();
            g.save();
            g.setTransform(new AffineTransform());
            //smooth scaling
            AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            g.drawImage(op.filter(image, null), new Vector());
            g.reset();
        }
    }
}
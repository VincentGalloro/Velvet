
package core.main.ui.active.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.IRenderable;
import core.main.ui.elements.IContainer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class WindowedView {
    
    private IContainer target;
    private final IRenderable preRender, postRender;
    private BufferedImage image;
    private int[] imageData;
    private Vector imageSize;
    
    public WindowedView(){
        preRender = this::preRender;
        postRender = this::postRender;
    }
    
    public void apply(IContainer target){
        detach();
        this.target = target;
        target.addPreChildRenderHandler(preRender);
        target.addPostChildRenderHandler(postRender);
    }
    public void detach(){
        if(target != null){
            target.removePreChildRenderHandler(preRender);
            target.removePostChildRenderHandler(postRender);
            target=null;
        }
    }
    
    private void clear(){
        for(int i = 0; i < imageData.length; i++){ imageData[i]=0xFF; }
    }
    
    private void preRender(VGraphics g) {
        //grab size of container
        Vector targetSize = target.getSize().ceil();
        //if size does not match, we must resize the buffer image
        if(image == null || !targetSize.equals(imageSize)){
            if((int)targetSize.x <= 0 || (int)targetSize.y <= 0){ image=null; return; }
            image = new BufferedImage((int)targetSize.x, (int)targetSize.y, BufferedImage.TYPE_INT_ARGB);
            imageSize = new Vector(targetSize);
            imageData = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        }
        clear();
        //grab our new graphics
        Graphics2D newG = image.createGraphics();
        /*newG.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                            RenderingHints.VALUE_FRACTIONALMETRICS_ON);*/
        //add our new graphics object to the graphics stack
        g.subGraphics(newG);
        //fill out our image with a white background
        g.setColor(new Color(0,0,0,0));
        g.fill(new Rectangle2D.Double(0, 0, imageSize.x, imageSize.y));
        //apply just the container's transform to our new graphics
        g.transform(target.getTransform());
    }

    private void postRender(VGraphics g) {
        if(image==null){ return; }
        //reset the graphics object back to the container's graphics
        g.resetGraphics();
        //grab the transform
        AffineTransform at = g.getTransform();
        //apply a reset, undo-ing the container's transform
        g.reset();
        //draw our image (this will render on the same transform provided to the container)
        g.drawImage(image, new Vector());
        //re-apply the container's transform
        g.transform(at);
        //save the transform (undo the reset), container will be unaware anything has changed
        g.save();
    }
}

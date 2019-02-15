package core.main.ui;

import core.main.VGraphics;
import core.main.structs.Vector;
import java.awt.geom.AffineTransform;

public class ScaleElement implements IContainer{
    
    private Vector size;
    private IElement element;

    public ScaleElement(){
        size = new Vector();
    }
    
    public void setElement(IElement e){ element = e; }
    public void setSize(Vector s){ size = s; }
    
    public Vector getSize() { return size; }
    public IElement getElement(){ return element; }
    public AffineTransform getTransform(){        
        AffineTransform at = new AffineTransform();
        Vector eSize = element.getSize();

        double scale = size.divide(eSize).min();
        at.translate((size.x - eSize.x*scale)/2, (size.y - eSize.y*scale)/2);
        at.scale(scale, scale);
        
        return at;
    }
    
    public void render(VGraphics g){
        if(element != null){
            g.save();
            g.transform(getTransform());
            element.render(g);
            g.reset();
        }
    }
}
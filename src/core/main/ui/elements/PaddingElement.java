package core.main.ui.elements;

import core.main.VGraphics;
import core.main.structs.Vector;
import java.awt.geom.AffineTransform;

public class PaddingElement implements IContainer{

    private IElement element;
    private double padding;
    
    public void setElement(IElement e){ element = e; }
    public void setPadding(double p){ padding = p; }
    public IElement getElement(){ return element; }
    
    public Vector getSize() {
        if(element == null){ return new Vector(); }
        return element.getSize().add(padding*2);
    }
    public AffineTransform getTransform(){
        AffineTransform at = new AffineTransform();
        at.translate(padding, padding);
        return at;
    }

    public void render(VGraphics g) {
        if(element != null){
            g.save();
            g.transform(getTransform());
            element.render(g);
            g.reset();
        }
    }
}
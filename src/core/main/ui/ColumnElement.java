package core.main.ui;

import core.main.VGraphics;
import core.main.structs.Vector;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

public class ColumnElement implements IListContainer{
    
    private ArrayList<IElement> elements;
    private double seperation;
    
    public ColumnElement(){
        elements = new ArrayList<>();
    }
    
    public void addElement(IElement e){ elements.add(e); }
    public void setSeperation(double s){ seperation = s; }
    
    public Vector getSize() {
        Vector size = new Vector();
        for(IElement e : elements){
            if(e.getSize().x > size.x){ size.x = e.getSize().x; }
            size.y += e.getSize().y;
        }
        size.y += seperation * (elements.size()-1);
        return size;
    }
    public int getElementCount() { return elements.size(); }
    public Iterator<IElement> getElements() { return elements.iterator(); }

    public AffineTransform getTransform(int index) {
        AffineTransform at = new AffineTransform();
        for(int i = 0; i < index; i++){
            at.translate(0, elements.get(i).getSize().y + seperation);
        }
        return at;
    }
    
    public void render(VGraphics g) {
        for(int i = 0; i < elements.size(); i++){
            g.save();
            g.transform(getTransform(i));
            elements.get(i).render(g);
            g.reset();
        }
    }
}
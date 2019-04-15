
package core.main.ui.composites.supercomposites;

import core.main.structs.Vector;
import core.main.ui.active.IDeltable;
import core.main.ui.active.IListTransformable;
import core.main.ui.composites.BasicScrollBar;
import core.main.ui.elements.BasicElement;
import core.main.ui.elements.BasicSizeable;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IElementBuilder;
import core.main.ui.elements.IListContainer;
import core.main.ui.elements.IScrollable;
import core.main.ui.elements.ISizeable;
import core.main.ui.elements.SeriesListContainer;
import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.PaddingElement;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.stream.Stream;

public abstract class BasicScrollListContainer extends BasicElement implements 
        IScrollable, IBoxable, ISizeable, IListContainer{

    public class Builder extends BasicElement.Builder{

        private final IElementBuilder boxBuilder, seriesListContainerBuilder, scrollBuilder;
        
        public Builder(){
            boxBuilder = box.getBuilder();
            seriesListContainerBuilder = seriesListContainer.getBuilder();
            scrollBuilder = scroll.getBuilder();
        }
        
        public void handleString(String field, String value){
            super.handleString(field, value);
            if(field.equals("size")){ setSize(toVector(value)); }
            if(field.startsWith("scroll ")){
                scrollBuilder.handleString(field.split(" ", 2)[1], value);
            }
            else{
                boxBuilder.handleString(field, value);
                seriesListContainerBuilder.handleString(field, value);
            }
        }
    }
    
    protected final BoxElement box;
    protected final SeriesListContainer intermediateContainer;
    protected final BasicSizeable listSize;
    protected final SeriesListContainer seriesListContainer;
    protected final BasicScrollBar scroll;
    
    public BasicScrollListContainer(SeriesListContainer ic, SeriesListContainer slc, BasicScrollBar bsb){
        box = new BoxElement();
        intermediateContainer = ic;
        listSize = new BasicSizeable();
        seriesListContainer = slc;
        scroll = bsb;
        
        PaddingElement pe = new PaddingElement();
        pe.setPadding(5);
        
        box.setElement(intermediateContainer);
        intermediateContainer.addElement(listSize);
        listSize.setElement(seriesListContainer);
        intermediateContainer.addElement(pe);
        pe.setElement(scroll);
        
        listSize.setStrict(true);
        //setSize(new Vector(600, 670));
        setSize(new Vector(200));
        intermediateContainer.setSeperation(5);
        
        box.new Builder().preRenderOutline();
        
        addUpdateHandler(box::update);
        addPostRenderHandler(box::render);
        addMouseScrollHandler(scroll::onMouseScroll);
    }
    
    public final IElement getHover(Vector mPos){
        IElement cHover = box.getHover(mPos);
        if(cHover != null){ return cHover; }
        return super.getHover(mPos);
    }
    
    public final Vector getSize() { return box.getSize(); }
    public final AffineTransform getTransform(int index) { 
        AffineTransform at = box.getTransform();
        at.concatenate(intermediateContainer.getTransform(0)); //since column lives in row index 0
        at.concatenate(listSize.getTransform());
        at.concatenate(seriesListContainer.getTransform(index));
        return at;
    }
    public final void setOutlineColor(Color o) { box.setOutlineColor(o); }
    public final void setFillColor(Color f) { box.setFillColor(f); }
    public final Color getOutlineColor() { return box.getOutlineColor(); }
    public final Color getFillColor() { return box.getFillColor(); }
    public final Shape getShape(){ return box.getShape(); }

    public final void addTransformHandler(IListTransformable t){ seriesListContainer.addTransformHandler(t); } 
    public final void removeTransformHandler(IListTransformable t){ seriesListContainer.removeTransformHandler(t); }
    public final void addElement(IElement e) { seriesListContainer.addElement(e); }
    public final int elementCount() { return seriesListContainer.elementCount(); }
    public final Stream<IElement> getElements() { return seriesListContainer.getElements(); }
    public final IElement getElement(int index) { return seriesListContainer.getElement(index); }
    public final IElement removeElement(int index) { return seriesListContainer.removeElement(index); }

    public final void addDeltaHandler(IDeltable deltaHandler) { scroll.addDeltaHandler(deltaHandler); }
    public final void setDelta(double d) { scroll.setDelta(d); }
    public final double getDelta() { return scroll.getDelta(); }
    public final double getLength() { return scroll.getLength(); }

    public final void setLength(double l) { scroll.setLength(l); }
    public final void setScrollablePercentage(double d) { scroll.setScrollablePercentage(d); }
    public final void setOutlineThickness(float t) { box.setOutlineThickness(t); }
    public final void setRounding(Double d) { box.setRounding(d); }
    public final double getScrollablePercentage() { return scroll.getScrollablePercentage(); }

}
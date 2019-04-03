
package core.main.ui.composites.supercomposites;

import core.main.Mouse;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.IDeltable;
import core.main.ui.active.impl.OffsetTransition;
import core.main.ui.composites.VScrollElement;
import core.main.ui.elements.BasicElement;
import core.main.ui.elements.BasicSizeable;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IElement;
import core.main.ui.elements.IElementBuilder;
import core.main.ui.elements.IListContainer;
import core.main.ui.elements.IScrollable;
import core.main.ui.elements.ISizeable;
import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.ColumnElement;
import core.main.ui.elements.impl.PaddingElement;
import core.main.ui.elements.impl.RowElement;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.stream.Stream;

public class ScrollColumn extends BasicElement implements IScrollable, IBoxable, ISizeable, IListContainer{

    public class Builder extends BasicElement.Builder{

        private final IElementBuilder boxBuilder, columnBuilder, scrollBuilder;
        
        public Builder(){
            boxBuilder = box.getBuilder();
            columnBuilder = column.getBuilder();
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
                columnBuilder.handleString(field, value);
            }
        }
    }
    
    private final BoxElement box;
    private final RowElement row;
    private final BasicSizeable colSize;
    private final ColumnElement column;
    private final VScrollElement scroll;
    
    public ScrollColumn(Mouse mouse){
        box = new BoxElement();
        row = new RowElement();
        colSize = new BasicSizeable();
        column = new ColumnElement();
        scroll = new VScrollElement(mouse);
        
        PaddingElement pe = new PaddingElement();
        pe.setPadding(5);
        
        box.setElement(pe);
        pe.setElement(row);
        row.addElement(colSize);
        colSize.setElement(column);
        row.addElement(scroll);
        
        colSize.setStrict(true);
        setSize(new Vector(600, 660));
        row.setSeperation(5);
        
        box.new Builder().preRenderOutline();
        
        addUpdateHandler(box::update);
        addPostRenderHandler(box::render);
        addMouseScrollHandler(scroll::onMouseScroll);
        
        OffsetTransition ot = new OffsetTransition(Motion.swish(60));
        ot.apply(column);
        scroll.addDeltaHandler(d -> ot.setOffset(new Vector(0,-d*(column.getSize().y-colSize.getSize().y))));
        addUpdateHandler(at -> scroll.setScrollablePercentage(colSize.getSize().y/column.getSize().y));
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public final IElement getHover(Vector mPos){
        IElement cHover = box.getHover(mPos);
        if(cHover != null){ return cHover; }
        return super.getHover(mPos);
    }
    
    public void setSize(Vector s){ colSize.setSize(s); scroll.setLength(s.y); }
    public Vector getSize() { return box.getSize(); }
    public AffineTransform getTransform(int index) { 
        AffineTransform at = box.getTransform();
        at.concatenate(row.getTransform(0)); //since column lives in row index 0
        at.concatenate(colSize.getTransform());
        at.concatenate(column.getTransform(index));
        return at;
    }
    public void setOutlineColor(Color o) { box.setOutlineColor(o); }
    public void setFillColor(Color f) { box.setFillColor(f); }
    public Color getOutlineColor() { return box.getOutlineColor(); }
    public Color getFillColor() { return box.getFillColor(); }

    public void addElement(IElement e) { column.addElement(e); }
    public int elementCount() { return column.elementCount(); }
    public Stream<IElement> getElements() { return column.getElements(); }
    public IElement getElement(int index) { return column.getElement(index); }
    public IElement removeElement(int index) { return column.removeElement(index); }

    public void addDeltaHandler(IDeltable deltaHandler) { scroll.addDeltaHandler(deltaHandler); }
    public void setDelta(double d) { scroll.setDelta(d); }
    public double getDelta() { return scroll.getDelta(); }
    public double getLength() { return scroll.getLength(); }

    public void setLength(double l) { scroll.setLength(l); }
    public void setScrollablePercentage(double d) { scroll.setScrollablePercentage(d); }
    public void setOutlineThickness(float t) { box.setOutlineThickness(t); }
    public void setRounding(Double d) { box.setRounding(d); }
    public double getScrollablePercentage() { return scroll.getScrollablePercentage(); }

}
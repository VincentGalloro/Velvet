
package core.main.ui.elements;

import core.main.VGraphics;
import core.main.ui.active.IDeltable;
import core.main.ui.active.IScrollEventable;
import core.main.ui.active.IUpdateable;
import core.main.ui.active.impl.DragObserver;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.function.Function;

public abstract class BasicScrollable extends BasicElement implements IScrollable{

    public class MouseScroller implements IUpdateable{
        
        private final Function<AffineTransform, Double> offsetGen;
        private final DragObserver dragObserver;

        public MouseScroller(Function<AffineTransform, Double> offsetGen){
            this.offsetGen = offsetGen;
            this.dragObserver = new DragObserver(BasicScrollable.this);
        }

        public void update(AffineTransform at) {
            if(dragObserver.isDragging()){
                double offset = offsetGen.apply(at) - (length*(1-getScrollablePercentage()))/2;
                double length = getLength() * getScrollablePercentage();
                if(length > 0){
                    setDelta(offset / length);
                }
            }
        }
    }

    public class WheelScroller implements IScrollEventable{

        private double scrollFactor;

        public WheelScroller(double scrollFactor){
            this.scrollFactor = scrollFactor;
        }

        public WheelScroller(double scrollFactor, boolean inverted){
            this(scrollFactor * (inverted ? -1 : 1));
        }

        public void onScroll(int amount) {
            setDelta(delta + amount*scrollFactor);
        }
    }
    
    public class Builder extends BasicElement.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("length")){ length = Double.parseDouble(value); }
            if(field.equals("bar color")){ color = toColor(value); }
            if(field.equals("bar thickness")){ thickness = Float.parseFloat(value); }
            if(field.equals("delta")){ delta = Double.parseDouble(value); }
            if(field.equals("scrollable percentage")){ setScrollablePercentage(Double.parseDouble(value)); }
        }
    }
    
    private final ArrayList<IDeltable> deltaHandlers;
    protected Color color;
    protected double length, delta;
    protected float thickness;
    
    public BasicScrollable(){
        this.deltaHandlers = new ArrayList<>();
        
        color = Color.BLACK;
        length = 100;
        thickness = 2;
        
        addPostRenderHandler(this::renderLine);
    }
    
    public final void addDeltaHandler(IDeltable deltaHandler){ this.deltaHandlers.add(deltaHandler); deltaHandler.onDelta(delta); }

    public final void setDelta(double d) { 
        delta = Math.min(Math.max(d, 0), 1); 
        for(IDeltable deltaHandler : deltaHandlers){ deltaHandler.onDelta(delta); }
    }
    public final void setLength(double l){ length = l; }
    
    public final double getDelta() { return delta; }
    public final double getLength(){ return length; }
    
    protected abstract Line2D.Double getLine();
    
    public void renderLine(VGraphics g){
        g.setColor(color);
        g.setStroke(new BasicStroke(thickness));
        g.draw(getLine());
        g.resetStroke();
    }
}

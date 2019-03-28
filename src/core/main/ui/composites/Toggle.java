package core.main.ui.composites;

import core.main.VGraphics;
import core.main.smooth.SmoothColor;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.impl.OffsetTransition;
import core.main.ui.elements.BasicToggleable;
import core.main.ui.elements.ElementBuilder;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IContainer;
import core.main.ui.elements.IPaddable;
import core.main.ui.elements.ISizeable;
import core.main.ui.elements.ITextable;
import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.CenteredElement;
import core.main.ui.elements.impl.PaddingElement;
import core.main.ui.elements.impl.LabelElement;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.function.Consumer;

public class Toggle extends BasicToggleable implements IBoxable, ITextable, IPaddable, ISizeable{
    
    public static class Builder extends BasicToggleable.Builder{

        private final Toggle toggle;
        private final ElementBuilder boxBuilder, textBuilder, padBuilder, sizeBuilder;
        //private final ColorData outlineData, fillData, textData;
        
        public Builder() {
            super(new Toggle());
            toggle = (Toggle)get();
            
            //colorData = new ColorData();
            
            boxBuilder = new BoxElement.Builder();
            textBuilder = new LabelElement.Builder();
            padBuilder = new PaddingElement.Builder();
            sizeBuilder = new CenteredElement.Builder();
            
            toggle.box = (IBoxable)boxBuilder.get();
            toggle.text = (ITextable)textBuilder.get();
            toggle.padding = (IPaddable)padBuilder.get();
            toggle.sizing = (ISizeable)sizeBuilder.get();
            
            ((IContainer)toggle.box).setElement(toggle.sizing);
            ((IContainer)toggle.sizing).setElement(toggle.padding);
            ((IContainer)toggle.padding).setElement(toggle.text);
            
            toggle.setPadding(5);
            toggle.setSize(new Vector(200, 40));
            
            SmoothColor smoothTextColor = new SmoothColor(Color.BLACK);
            smoothTextColor.setMotionFactory(Motion.linear(20));
            SmoothColor smoothFillColor = new SmoothColor(Color.WHITE);
            smoothFillColor.setMotionFactory(Motion.linear(20));
            
            toggle.addUpdateHandler(at -> smoothTextColor.update());
            toggle.addUpdateHandler(at -> toggle.setTextColor(smoothTextColor.getSmooth()));
            
            toggle.addUpdateHandler(at -> smoothFillColor.update());
            toggle.addUpdateHandler(at -> toggle.setFillColor(smoothFillColor.getSmooth()));
            
            toggle.addMousePressHandler(() -> toggle.toggle());
            
            Consumer<Color> multiText = c -> {
                toggle.setOutlineColor(c);
                smoothTextColor.overrideColor(c);
            };
            
            /*toggle.addToggleOnHandler(() -> multiText.accept(colorData.getColor(false, true, false)));
            toggle.addToggleOffHandler(() -> multiText.accept(colorData.getColor(false, false, false)));
            
            toggle.addToggleOnHandler(() -> smoothFillColor.overrideColor(colorData.getColor(false, true, true)));
            toggle.addToggleOffHandler(() -> smoothFillColor.overrideColor(colorData.getColor(false, false, true)));
            
            toggle.addHoverStartHandler(() -> smoothTextColor.setColor(colorData.getColor(true, toggle.isToggled(), false)));
            toggle.addHoverEndHandler(() -> smoothTextColor.setColor(colorData.getColor(false, toggle.isToggled(), false)));
            
            toggle.addHoverStartHandler(() -> smoothFillColor.setColor(colorData.getColor(true, toggle.isToggled(), true)));
            toggle.addHoverEndHandler(() -> smoothFillColor.setColor(colorData.getColor(false, toggle.isToggled(), true)));*/
            
            OffsetTransition ot = new OffsetTransition();
            toggle.addUpdateHandler(ot);
            toggle.addRenderHandler(ot);
            toggle.addHoverStartHandler(() -> ot.setOffset(new Vector(-1, -5)));
            toggle.addHoverEndHandler(() -> ot.setOffset(new Vector()));
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            boxBuilder.handleString(field, value);
            textBuilder.handleString(field, value);
            padBuilder.handleString(field, value);
            sizeBuilder.handleString(field, value);
        }
    }
    
    private IBoxable box;
    private ITextable text;
    private IPaddable padding;
    private ISizeable sizing;
    
    private Toggle(){}
    
    public void setOutlineColor(Color o) { box.setOutlineColor(o); }
    public void setFillColor(Color f) { box.setFillColor(f); }
    public void setThickness(float t) { box.setThickness(t); }
    public void setText(String t) { text.setText(t); }
    public void setTextColor(Color c) { text.setTextColor(c); }
    public void setPadding(double p) { padding.setPadding(p); }
    public void setSize(Vector s) { sizing.setSize(s); }
    
    public void containerUpdate(AffineTransform at){ box.update(at); }
    
    public Color getOutlineColor(){ return box.getOutlineColor(); }
    public Color getFillColor(){ return box.getFillColor(); }
    public Vector getSize() { return box.getSize(); }
    public String getText() { return text.getText(); }
    public Color getTextColor() { return text.getTextColor(); }
    public boolean supportsNewline(){ return text.supportsNewline(); }
    public AffineTransform getCharTransform(int charIndex) { return text.getCharTransform(charIndex); }
    
    public void onRender(VGraphics g) {
        box.render(g);
    }
}
package core.main.ui.composites;

import core.main.VGraphics;
import core.main.smooth.SmoothColor;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.IEventable;
import core.main.ui.active.adapters.impl.BoxOutlineAdapter;
import core.main.ui.active.adapters.impl.ColorMultiAdapter;
import core.main.ui.active.impl.ColorTransition;
import core.main.ui.active.adapters.impl.SmoothColorAdapter;
import core.main.ui.active.adapters.impl.TextColorAdapter;
import core.main.ui.active.adapters.impl.SmoothSetterAdapter;
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

public class Toggle extends BasicToggleable implements IBoxable, ITextable, IPaddable, ISizeable{

    public static class Builder extends BasicToggleable.Builder{

        private final Toggle toggle;
        private final ElementBuilder boxBuilder, textBuilder, padBuilder, sizeBuilder;
        
        public Builder() {
            super(new Toggle());
            toggle = (Toggle)get();
            
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
            
            SmoothColor smoothColor = new SmoothColor(Color.BLACK);
            smoothColor.setMotionFactory(Motion.linear(20));
            SmoothColorAdapter textColorAdapter = new SmoothColorAdapter(toggle.text.getTextColorAdapter(), smoothColor);
            
            toggle.addUpdateHandler(textColorAdapter);
            toggle.addMousePressHandler(new IEventable(){
                public void onEvent(){ toggle.toggle(); }
            });
            
            ColorMultiAdapter multi = new ColorMultiAdapter();
            multi.addAdapter(toggle.box.getOutlineColorAdapter());
            multi.addAdapter(textColorAdapter);
            multi.addAdapter(new SmoothSetterAdapter(textColorAdapter));
            
            toggle.addToggleOnHandler(new ColorTransition(multi, new Color(0, 255, 0)));
            toggle.addToggleOffHandler(new ColorTransition(multi, Color.BLACK));
            
            toggle.addHoverStartHandler(new IEventable(){
                public void onEvent(){
                    textColorAdapter.setColor(toggle.isToggled() ? new Color(200, 255, 200) : new Color(200, 200, 200));
                }
            });
            toggle.addHoverEndHandler(new IEventable(){
                public void onEvent(){
                    textColorAdapter.setColor(toggle.isToggled() ? new Color(0, 255, 0) : Color.BLACK);
                }
            });
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
    
    public BoxOutlineAdapter getOutlineColorAdapter() { return box.getOutlineColorAdapter(); }
    public TextColorAdapter getTextColorAdapter() { return text.getTextColorAdapter(); }
    public Vector getSize() { return box.getSize(); }
    public String getText() { return text.getText(); }
    public Color getTextColor() { return text.getTextColor(); }
    
    public void render(VGraphics g) {
        box.render(g);
    }
}
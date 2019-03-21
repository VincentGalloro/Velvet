package core.main.ui.composites;

import core.main.VGraphics;
import core.main.smooth.SmoothColor;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.IColorAdapter;
import core.main.ui.active.IColorGetterAdapter;
import core.main.ui.active.IColorSetterAdapter;
import core.main.ui.active.IEventable;
import core.main.ui.active.impl.ColorTransition;
import core.main.ui.active.impl.SmoothColorAdapter;
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

    private static class ColorData{
        
        private Color[] data;
        
        public ColorData(){
            data = new Color[4];
            
            //default colors:
            data[0] = Color.BLACK;
            data[1] = new Color(200, 200, 200);
            data[2] = Color.GREEN;
            data[3] = new Color(200, 255, 200);
        }
        
        private int toInd(boolean hover, boolean toggled){
            return (hover ? 1 : 0) + (toggled ? 2 : 0);
        }
        
        public IColorGetterAdapter getAdapter(boolean hover, boolean toggled){
            return new IColorGetterAdapter(){
                public Color getColor(){ return data[toInd(hover, toggled)]; }
            };
        }
        
        public void setColor(boolean hover, boolean toggled, Color c){
            data[toInd(hover, toggled)] = c;
        }
    }
    
    public static class Builder extends BasicToggleable.Builder{

        private final Toggle toggle;
        private final ElementBuilder boxBuilder, textBuilder, padBuilder, sizeBuilder;
        private final ColorData colorData;
        
        public Builder() {
            super(new Toggle());
            toggle = (Toggle)get();
            
            colorData = new ColorData();
            
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
            
            IColorSetterAdapter multi = new IColorSetterAdapter(){
                public void setColor(Color c){
                    toggle.box.getOutlineColorAdapter().setColor(c);
                    textColorAdapter.setColor(c);
                    textColorAdapter.getSmoothSetterAdapter().setColor(c);
                }
            };
            
            toggle.addToggleOnHandler(new ColorTransition(multi, colorData.getAdapter(false, true)));
            toggle.addToggleOffHandler(new ColorTransition(multi, colorData.getAdapter(false, false)));
            
            toggle.addHoverStartHandler(new IEventable(){
                public void onEvent(){
                    textColorAdapter.setColor(colorData.getAdapter(true, toggle.isToggled()).getColor());
                }
            });
            toggle.addHoverEndHandler(new IEventable(){
                public void onEvent(){
                    textColorAdapter.setColor(colorData.getAdapter(false, toggle.isToggled()).getColor());
                }
            });
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            boxBuilder.handleString(field, value);
            textBuilder.handleString(field, value);
            padBuilder.handleString(field, value);
            sizeBuilder.handleString(field, value);
            if(field.equals("toggle color")){ colorData.setColor(false, true, toColor(value)); }
            if(field.equals("hover toggle color") || field.equals("toggle hover color")){ 
                colorData.setColor(true, true, toColor(value)); 
            }
            if(field.equals("hover color")){ colorData.setColor(true, false, toColor(value)); }
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
    
    public IColorAdapter getOutlineColorAdapter() { return box.getOutlineColorAdapter(); }
    public IColorAdapter getTextColorAdapter() { return text.getTextColorAdapter(); }
    public Vector getSize() { return box.getSize(); }
    public String getText() { return text.getText(); }
    public Color getTextColor() { return text.getTextColor(); }
    public boolean supportsNewline(){ return text.supportsNewline(); }
    public AffineTransform getCharTransform(int charIndex) { return text.getCharTransform(charIndex); }
    
    public void onRender(VGraphics g) {
        box.render(g);
    }
}
package core.main.ui.composites;

import core.main.structs.Vector;
import core.main.ui.elements.BasicToggleable;
import core.main.ui.elements.ElementBuilder;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IPaddable;
import core.main.ui.elements.ISizeable;
import core.main.ui.elements.ITextable;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class Toggle extends BasicToggleable implements IBoxable, ITextable, IPaddable, ISizeable{
    
    public static class ColorProfile{
        
        private Button.ColorProfile colorProfile, toggleColorProfile;
        
        //public ColorProfile
    }
    
    public static class Builder extends BasicToggleable.Builder{

        private final Toggle toggle;
        private final ElementBuilder buttonBuilder;
        
        public Builder() {
            super(new Toggle());
            toggle = (Toggle)get();
            
            buttonBuilder = new Button.Builder();
            
            toggle.button = (Button)buttonBuilder.get();
            
            toggle.addPostRenderHandler(toggle.button::render);
            toggle.addUpdateHandler(toggle.button::update);
            toggle.addHoverStartHandler(toggle.button::onHoverStart);
            toggle.addHoverEndHandler(toggle.button::onHoverEnd);
            toggle.addMousePressHandler(toggle.button::onMousePress);
            
            toggle.addMousePressHandler(toggle::toggle);
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            buttonBuilder.handleString(field, value);
            if(field.equals("text")){ toggle.addToggleOffHandler(() -> toggle.setText(value)); }
            if(field.equals("toggle text")){ toggle.addToggleOnHandler(() -> toggle.setText(value)); }
        }
    }
    
    private Button button;
    
    private Toggle(){}
    
    public void setOutlineColor(Color o) { button.setOutlineColor(o); }
    public void setFillColor(Color f) { button.setFillColor(f); }
    public void setThickness(float t) { button.setThickness(t); }
    public void setText(String t) { button.setText(t); }
    public void setTextColor(Color c) { button.setTextColor(c); }
    public void setPadding(double p) { button.setPadding(p); }
    public void setSize(Vector s) { button.setSize(s); }
    
    public void containerUpdate(AffineTransform at){ button.update(at); }
    
    public Color getOutlineColor(){ return button.getOutlineColor(); }
    public Color getFillColor(){ return button.getFillColor(); }
    public Vector getSize() { return button.getSize(); }
    public String getText() { return button.getText(); }
    public Color getTextColor() { return button.getTextColor(); }
    public boolean supportsNewline(){ return button.supportsNewline(); }
    public AffineTransform getCharTransform(int charIndex) { return button.getCharTransform(charIndex); }
}
package core.main.ui.composites;

import core.main.structs.GridDirection;
import core.main.structs.Vector;
import core.main.ui.elements.BasicToggleable;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IPaddable;
import core.main.ui.elements.ISizeable;
import core.main.ui.elements.ITextable;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import core.main.ui.elements.IElementBuilder;

public class Toggle extends BasicToggleable implements IBoxable, ITextable, IPaddable, ISizeable{
    
    public static class ColorProfile{
        
        private Button.ColorProfile colorProfile, toggleColorProfile;
        
        public ColorProfile(){
            colorProfile = new Button.ColorProfile();
            toggleColorProfile = new Button.ColorProfile();
        }
        
        public void handleString(String field, Color color){
            if(field.startsWith("toggle ")){ toggleColorProfile.handleString(field.split(" ", 2)[1], color); }
            else{ colorProfile.handleString(field, color); }
        }
    }
    
    public class Builder extends BasicToggleable.Builder{
        
        private final IElementBuilder buttonBuilder;
        
        public Builder() {
            buttonBuilder = button.new Builder();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            buttonBuilder.handleString(field, value);
            if(field.equals("text")){ addToggleOffHandler(() -> setText(value)); }
            if(field.equals("toggle text")){ addToggleOnHandler(() -> setText(value)); }
            if(field.endsWith(" color")){ colorProfile.handleString(field, toColor(value)); }
        }
    }
    
    private ColorProfile colorProfile;
    private final Button button;
    
    public Toggle(){
        button = new Button();
        
        colorProfile = new ColorProfile();
        colorProfile.colorProfile = button.getColorProfile();

        colorProfile.toggleColorProfile.getTextProfile().setColor(Color.GREEN);
        colorProfile.toggleColorProfile.getTextProfile().setHoverColor(new Color(200, 255, 200));
        colorProfile.toggleColorProfile.getTextProfile().setClickColor(Color.GREEN);
        colorProfile.colorProfile.getTextProfile().setClickColor(Color.BLACK);

        colorProfile.colorProfile.getBoxOutlineProfile().setClickColor(Color.BLACK);
        colorProfile.toggleColorProfile.getBoxOutlineProfile().setClickColor(Color.GREEN);
        colorProfile.colorProfile.getBoxOutlineProfile().setColor(null);
        colorProfile.toggleColorProfile.getBoxOutlineProfile().setColor(null);

        addPostRenderHandler(button::render);
        addUpdateHandler(button::update);
        addHoverStartHandler(button::onHoverStart);
        addHoverEndHandler(button::onHoverEnd);

        addMousePressHandler(this::toggle);
        addToggleHandler(button::onMousePress);

        addToggleOnHandler(() -> button.setColorProfile(colorProfile.toggleColorProfile));
        addToggleOffHandler(() -> button.setColorProfile(colorProfile.colorProfile));
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public void setOutlineColor(Color o) { button.setOutlineColor(o); }
    public void setFillColor(Color f) { button.setFillColor(f); }
    public void setThickness(float t) { button.setThickness(t); }
    public void setText(String t) { button.setText(t); }
    public void setTextColor(Color c) { button.setTextColor(c); }
    public void setPadding(double p) { button.setPadding(p); }
    public void setPadding(double p, GridDirection dir) { button.setPadding(p, dir); }
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
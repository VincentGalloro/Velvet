package core.main.ui.composites;

import core.main.VGraphics;
import core.main.smooth.SmoothColor;
import core.main.smooth.motion.Motion;
import core.main.structs.Vector;
import core.main.ui.active.IUpdateable;
import core.main.ui.active.impl.OffsetTransition;
import core.main.ui.elements.*;
import core.main.ui.elements.impl.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Button extends BasicElement implements IBoxable, ITextable, IPaddable, ISizeable{
    
    private static class ColorController implements IUpdateable{
        
        private SmoothColor color;
        
        public ColorController(Color c){
            color = new SmoothColor(c);
            color.setMotionFactory(Motion.linear(15));
        }
        
        public void bind(IElement source, Supplier<ColorProfile.CHCProfile> profile, Consumer<Color> target){
            source.addHoverStartHandler(() -> { if(profile.get().hoverColor != null){ color.setColor(profile.get().hoverColor); } });
            source.addHoverEndHandler(() -> { if(profile.get().color != null){ color.setColor(profile.get().color); } });
            source.addMousePressHandler(() -> { if(profile.get().clickColor != null){ color.setSmooth(profile.get().clickColor); } });
            source.addUpdateHandler(this);
            source.addUpdateHandler(at -> target.accept(color.getSmooth()));
        }

        public void update(AffineTransform at) {
            color.update();
        }
    }
    
    public static class ColorProfile{
        
        public static class CHCProfile{
            private Color color, hoverColor, clickColor;

            public Color getColor() { return color; } 
            public void setColor(Color color) { this.color = color; } 

            public Color getHoverColor() { return hoverColor; } 
            public void setHoverColor(Color hoverColor) { this.hoverColor = hoverColor; } 

            public Color getClickColor() { return clickColor; } 
            public void setClickColor(Color clickColor) { this.clickColor = clickColor; }

            public void handleString(String field, Color color){
                if(field.equals("color")){ this.color = color; }
                if(field.equals("hover color")){ hoverColor = color; }
                if(field.equals("click color")){ clickColor = color; }
            }
        }
        
        private CHCProfile boxOutlineProfile, boxFillProfile, textProfile;
        
        public ColorProfile(){
            boxOutlineProfile = new CHCProfile();
            boxFillProfile = new CHCProfile();
            textProfile = new CHCProfile();
        }

        public CHCProfile getBoxOutlineProfile() { return boxOutlineProfile; }
        public void setBoxOutlineProfile(CHCProfile boxOutlineProfile) { this.boxOutlineProfile = boxOutlineProfile; }

        public CHCProfile getBoxFillProfile() { return boxFillProfile; }
        public void setBoxFillProfile(CHCProfile boxFillProfile) { this.boxFillProfile = boxFillProfile; }

        public CHCProfile getTextProfile() { return textProfile; }
        public void setTextProfile(CHCProfile textProfile) { this.textProfile = textProfile; }

        public void handleString(String field, Color color){
            if(field.startsWith("outline ")){ boxOutlineProfile.handleString(field.split(" ", 2)[1], color); }
            if(field.startsWith("fill ")){ boxFillProfile.handleString(field.split(" ", 2)[1], color); }
            if(field.startsWith("text ")){ textProfile.handleString(field.split(" ", 2)[1], color); }
        }
    }
    
    public static class Builder extends BasicElement.Builder{

        private final Button button;
        private final ElementBuilder boxBuilder, textBuilder, padBuilder, sizeBuilder;
        
        public Builder() {
            super(new Button());
            button = (Button)get();
            
            boxBuilder = new BoxElement.Builder();
            textBuilder = new LabelElement.Builder();
            padBuilder = new PaddingElement.Builder();
            sizeBuilder = new CenteredElement.Builder();
            
            button.box = (IBoxable)boxBuilder.get();
            button.text = (ITextable)textBuilder.get();
            button.padding = (IPaddable)padBuilder.get();
            button.sizing = (ISizeable)sizeBuilder.get();
            
            button.addPostRenderHandler(button.box::render);
            
            ((IContainer)button.box).setElement(button.sizing);
            ((IContainer)button.sizing).setElement(button.padding);
            ((IContainer)button.padding).setElement(button.text);
            
            button.setPadding(5);
            button.setSize(new Vector(200, 40));
            
            ColorController boxOutlineCC = new ColorController(Color.BLACK);
            ColorController boxFillCC = new ColorController(Color.WHITE);
            ColorController textCC = new ColorController(Color.BLACK);
            
            boxOutlineCC.bind(button, () -> button.colorProfile.boxOutlineProfile, c -> button.setOutlineColor(c));
            boxFillCC.bind(button, () -> button.colorProfile.boxFillProfile, c -> button.setFillColor(c));
            textCC.bind(button, () -> button.colorProfile.textProfile, c -> button.setTextColor(c));
            
            button.colorProfile = new ColorProfile();
            
            button.colorProfile.textProfile.color = Color.BLACK;
            button.colorProfile.textProfile.hoverColor = new Color(200, 200, 200);
            button.colorProfile.textProfile.clickColor = Color.GREEN;
            
            OffsetTransition ot = new OffsetTransition();
            ot.apply(button);
            button.addHoverStartHandler(() -> ot.setOffset(new Vector(-1, -5)));
            button.addHoverEndHandler(() -> ot.setOffset(new Vector()));
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            boxBuilder.handleString(field, value);
            textBuilder.handleString(field, value);
            padBuilder.handleString(field, value);
            sizeBuilder.handleString(field, value);
            if(field.endsWith("color")){ button.colorProfile.handleString(field, toColor(value)); }
        }
    }
    
    private IBoxable box;
    private ITextable text;
    private IPaddable padding;
    private ISizeable sizing;
    private ColorProfile colorProfile;
    
    private Button(){}
    
    public void setOutlineColor(Color o) { box.setOutlineColor(o); }
    public void setFillColor(Color f) { box.setFillColor(f); }
    public void setThickness(float t) { box.setThickness(t); }
    public void setText(String t) { text.setText(t); }
    public void setTextColor(Color c) { text.setTextColor(c); }
    public void setPadding(double p) { padding.setPadding(p); }
    public void setSize(Vector s) { sizing.setSize(s); }
    public void setColorProfile(ColorProfile colorProfile) { this.colorProfile = colorProfile; }
    
    public void containerUpdate(AffineTransform at){ box.update(at); }
    
    public Color getOutlineColor(){ return box.getOutlineColor(); }
    public Color getFillColor(){ return box.getFillColor(); }
    public Vector getSize() { return box.getSize(); }
    public String getText() { return text.getText(); }
    public Color getTextColor() { return text.getTextColor(); }
    public boolean supportsNewline(){ return text.supportsNewline(); }
    public AffineTransform getCharTransform(int charIndex) { return text.getCharTransform(charIndex); }
    public ColorProfile getColorProfile() { return colorProfile; }
}
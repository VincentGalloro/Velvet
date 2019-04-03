package core.main.ui.composites;

import core.main.smooth.SmoothColor;
import core.main.smooth.motion.Motion;
import core.main.structs.GridDirection;
import core.main.structs.Vector;
import core.main.ui.active.IUpdateable;
import core.main.ui.active.impl.OffsetTransition;
import core.main.ui.elements.*;
import core.main.ui.elements.impl.*;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Button extends BasicElement implements IBoxable, ITextable, IPaddable, ISizeable{
    
    private static class ColorController implements IUpdateable{
        
        private SmoothColor color;
        private Runnable lastHoverCall;
        
        public ColorController(Color c){
            color = new SmoothColor(c);
            color.setMotionFactory(Motion.linear(15));
        }
        
        public void bind(IElement source, Supplier<ColorProfile.CHCProfile> profile, Consumer<Color> target){
            Runnable hoverStart = () -> { color.setColor(profile.get().hoverColor); };
            Runnable hoverEnd = () -> { color.setColor(profile.get().color); };
            source.addHoverStartHandler(() -> {hoverStart.run(); lastHoverCall = hoverStart; });
            source.addHoverEndHandler(() -> {hoverEnd.run(); lastHoverCall = hoverEnd; });
            source.addMousePressHandler(() -> { if(profile.get().clickColor != null){ color.setSmooth(profile.get().clickColor); } });
            source.addUpdateHandler(this);
            source.addUpdateHandler(at -> target.accept(color.getSmooth()));
        }
        
        public void onProfileSwap(){
            lastHoverCall.run();
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
    
    public class Builder extends BasicElement.Builder{

        private final IElementBuilder boxBuilder, textBuilder, padBuilder, sizeBuilder;
        
        public Builder() {
            boxBuilder = box.getBuilder();
            textBuilder = text.getBuilder();
            padBuilder = padding.getBuilder();
            sizeBuilder = sizing.getBuilder();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            boxBuilder.handleString(field, value);
            textBuilder.handleString(field, value);
            padBuilder.handleString(field, value);
            sizeBuilder.handleString(field, value);
            if(field.endsWith(" color")){ colorProfile.handleString(field, toColor(value)); }
        }
    }
    
    private final BoxElement box;
    private final LabelElement text;
    private final PaddingElement padding;
    private final CenteredElement sizing;
    private ColorProfile colorProfile;
    private final ArrayList<ColorController> colorControllers;
    
    public Button(){
        colorControllers = new ArrayList<>();
        
        box = new BoxElement();
        text = new LabelElement();
        padding = new PaddingElement();
        sizing = new CenteredElement();

        addUpdateHandler(box::update);
        addPostRenderHandler(box::render);

        box.setElement(sizing);
        sizing.setElement(padding);
        padding.setElement(text);

        setPadding(5);
        setSize(new Vector(200, 40));

        ColorController boxOutlineCC = new ColorController(Color.BLACK);
        ColorController boxFillCC = new ColorController(Color.WHITE);
        ColorController textCC = new ColorController(Color.BLACK);

        colorControllers.add(boxOutlineCC);
        colorControllers.add(boxFillCC);
        colorControllers.add(textCC);

        boxOutlineCC.bind(this, ()->getColorProfile().getBoxOutlineProfile(), c -> setOutlineColor(c));
        boxFillCC.bind(this, ()->getColorProfile().getBoxFillProfile(), c -> setFillColor(c));
        textCC.bind(this, ()->getColorProfile().getTextProfile(), c -> setTextColor(c));

        colorProfile = new ColorProfile();

        colorProfile.textProfile.color = Color.BLACK;
        colorProfile.textProfile.hoverColor = new Color(200, 200, 200);
        colorProfile.textProfile.clickColor = Color.GREEN;

        OffsetTransition ot = new OffsetTransition();
        ot.apply(this);
        addHoverStartHandler(() -> ot.setOffset(new Vector(-1, -5)));
        addHoverEndHandler(() -> ot.setOffset(new Vector()));
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public void setOutlineColor(Color o) { box.setOutlineColor(o); }
    public void setFillColor(Color f) { box.setFillColor(f); }
    public void setOutlineThickness(float t) { box.setOutlineThickness(t); }
    public void setRounding(Double d){ box.setRounding(d); }
    public void setText(String t) { text.setText(t); }
    public void setTextColor(Color c) { text.setTextColor(c); }
    public void setPadding(double p) { padding.setPadding(p); }
    public void setPadding(double p, GridDirection dir) { padding.setPadding(p, dir); }
    public void setSize(Vector s) { sizing.setSize(s); }
    public void setColorProfile(ColorProfile colorProfile) { 
        this.colorProfile = colorProfile; 
        for(ColorController cc : colorControllers){ cc.onProfileSwap(); }
    }
    
    public Color getOutlineColor(){ return box.getOutlineColor(); }
    public Color getFillColor(){ return box.getFillColor(); }
    public Vector getSize() { return box.getSize(); }
    public String getText() { return text.getText(); }
    public Color getTextColor() { return text.getTextColor(); }
    public boolean supportsNewline(){ return text.supportsNewline(); }
    public AffineTransform getCharTransform(int charIndex) { return text.getCharTransform(charIndex); }
    public ColorProfile getColorProfile() { return colorProfile; }
}
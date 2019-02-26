package core.main.ui.composites;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.active.impl.TextClickColorTransition;
import core.main.ui.active.impl.TextHoverColorTransition;
import core.main.ui.elements.BasicElement;
import core.main.ui.elements.ElementBuilder;
import core.main.ui.elements.IBoxable;
import core.main.ui.elements.IContainer;
import core.main.ui.elements.IPaddable;
import core.main.ui.elements.ISizeable;
import core.main.ui.elements.ITextable;
import core.main.ui.elements.impl.BoxElement;
import core.main.ui.elements.impl.CenteredElement;
import core.main.ui.elements.impl.PaddingElement;
import core.main.ui.elements.impl.TextElement;
import java.awt.Color;
import java.awt.geom.AffineTransform;

public class Button extends BasicElement implements IBoxable, ITextable, IPaddable, ISizeable{

    public static class Builder extends BasicElement.Builder{

        private Button button;
        private ElementBuilder boxBuilder, textBuilder, padBuilder, sizeBuilder;
        
        public Builder() {
            super(new Button());
            button = (Button)get();
            
            boxBuilder = new BoxElement.Builder();
            textBuilder = new TextElement.Builder();
            padBuilder = new PaddingElement.Builder();
            sizeBuilder = new CenteredElement.Builder();
            
            button.box = (IBoxable)boxBuilder.get();
            button.text = (ITextable)textBuilder.get();
            button.padding = (IPaddable)padBuilder.get();
            button.sizing = (ISizeable)sizeBuilder.get();
            
            ((IContainer)button.box).setElement(button.sizing);
            ((IContainer)button.sizing).setElement(button.padding);
            ((IContainer)button.padding).setElement(button.text);
            
            button.setPadding(5);
            button.setSize(new Vector(200, 40));
            
            button.addHoverHandler(new TextHoverColorTransition(button.text, Color.BLACK, new Color(180, 180, 180)));
            button.addClickHandler(new TextClickColorTransition(button.text, new Color(100, 255, 100)));
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
    
    private Button(){}
    
    public void setOutlineColor(Color o) { box.setOutlineColor(o); }
    public void setFillColor(Color f) { box.setFillColor(f); }
    public void setThickness(float t) { box.setThickness(t); }
    public void setText(String t) { text.setText(t); }
    public void setTextColor(Color c) { text.setTextColor(c); }
    public void setTextSmoothColor(Color c) { text.setTextSmoothColor(c); }
    public void setPadding(double p) { padding.setPadding(p); }
    public void setSize(Vector s) { sizing.setSize(s); }
    
    public void onUpdate(AffineTransform at){ box.update(at); }
    
    public Vector getSize() { return box.getSize(); }
    public String getText() { return text.getText(); }
    public Color getTextColor() { return text.getTextColor(); }
    
    public void render(VGraphics g) {
        box.render(g);
    }
}
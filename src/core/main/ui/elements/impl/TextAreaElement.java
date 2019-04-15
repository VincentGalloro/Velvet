package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicTextable;
import core.main.ui.elements.IElementBuilder;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class TextAreaElement extends BasicTextable{

    public class Builder extends BasicTextable.Builder{
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("text width")){ setWidth(Double.parseDouble(value)); }
            if(field.equals("line sep")){ sep = Double.parseDouble(value); }
        }
    }
    
    private final ArrayList<Integer> breakpoints;
    private final ArrayList<String> breakpointSegments;
    private double width, sep;
    
    public TextAreaElement(){
        width = 200;
        breakpoints = new ArrayList<>();
        breakpointSegments = new ArrayList<>();
        calculateBreakpoints();
        
        addPostRenderHandler(this::postRender);
        addTextChangeHandler(s -> calculateBreakpoints());
        addFontMetricsChangeHandler(fm -> calculateBreakpoints());
    }
    
    public IElementBuilder getBuilder(){ return new Builder(); }
    
    public boolean supportsNewline(){ return true; }
    
    private void calculateBreakpoints(){
        breakpoints.clear();
        breakpointSegments.clear();
        
        breakpoints.add(0);
        char[] chars = text.toCharArray();
        int last = 0, length = 1;
        while(last+length < chars.length){
            Integer whitespaceMarker=null;
            while(last+length < chars.length && chars[last+length-1] != '\n' && fontMetrics.charsWidth(chars, last, length+1) <= width){ 
                if(chars[last+length] == ' '){ whitespaceMarker = length; }
                length++; 
            }
            if(whitespaceMarker != null && last+length < chars.length){ length = whitespaceMarker; }
            last += length;
            breakpoints.add(last);
            length = 1;
        }
        //if the last line begins with a single character, it will not be recognized in the main loop
        //this line makes sure the last pointer is at the end of the text
        if(breakpoints.get(breakpoints.size()-1) != chars.length){ breakpoints.add(chars.length); }
        //since newlines print in the same line as their text, if the box ends with a newline, the text area
        //will not have an additional empty line. This statement fixes that
        if(chars.length>0 && chars[chars.length-1] == '\n'){ breakpoints.add(chars.length); }
        
        //setup segments from breakpoints
        for(int i = 0; i < breakpoints.size()-1; i++){
            breakpointSegments.add(text.substring(breakpoints.get(i), breakpoints.get(i+1)));
        }
    }
    
    private void setWidth(double w){
        width = w;
        calculateBreakpoints();
    }
    
    public Vector getSize() { 
        int lineCount = Math.max(breakpoints.size()-1, 1);
        return new Vector(width, (fontMetrics.getHeight() + sep)*lineCount - sep); 
    }
    
    public AffineTransform getCharTransform(int charIndex) {
        int index = breakpoints.size()-1;
        while(index>0 && charIndex < breakpoints.get(index)){ index--; }
        if(index>0 && charIndex == text.length()){ index--; }
        
        AffineTransform at = new AffineTransform();
        at.translate(fontMetrics.stringWidth(text.substring(breakpoints.get(index), charIndex)), getYOffs(index));
        return at;
    }
    
    private double getYOffs(int row){
        return fontMetrics.getAscent() + (fontMetrics.getHeight() + sep)*row;
    }
    
    public void postRender(VGraphics g) {
        for(int i = 0; i < breakpointSegments.size(); i++){
            g.drawString(breakpointSegments.get(i), new Vector(0, getYOffs(i)));
        }
    }
}
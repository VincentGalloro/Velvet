package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicTextable;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class TextAreaElement extends BasicTextable{

    public static class Builder extends BasicTextable.Builder{
        
        private TextAreaElement textArea;
        
        public Builder() { 
            super(new TextAreaElement()); 
            textArea = (TextAreaElement)get();
        }
        
        public void handleString(String field, String value) {
            super.handleString(field, value);
            if(field.equals("text width")){ textArea.width = Double.parseDouble(value); }
            if(field.equals("line sep")){ textArea.sep = Double.parseDouble(value); }
        }
    }
    
    private double width, sep;
    
    public TextAreaElement(){
        width = 200;
    }
    
    public boolean supportsNewline(){ return true; }
    
    private ArrayList<Integer> getBreakpoints(){
        ArrayList<Integer> points = new ArrayList<>();
        points.add(0);
        int last = 0, length = 1;
        while(last+length < text.length()){
            while(last+length < text.length() && text.charAt(last+length-1) != '\n' && fontMetrics.stringWidth(text.substring(last, last+length+1)) <= width){ 
                length++; 
            }
            last += length;
            points.add(last);
            length = 1;
        }
        //if the last line begins with a single character, it will not be recognized in the main loop
        //this line makes sure the last pointer is at the end of the text
        if(points.get(points.size()-1) != text.length()){ points.add(text.length()); }
        //since newlines print in the same line as their text, if the box ends with a newline, the text area
        //will not have an additional empty line. This statement fixes that
        if(!text.isEmpty() && text.charAt(text.length()-1) == '\n'){ points.add(text.length()); }
        return points;
    }
    
    public Vector getSize() { 
        int lineCount = Math.max(getBreakpoints().size()-1, 1);
        return new Vector(width, (fontMetrics.getHeight() + sep)*lineCount - sep); 
    }
    
    public AffineTransform getCharTransform(int charIndex) {
        ArrayList<Integer> breakpoints = getBreakpoints();
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
    
    public void onRender(VGraphics g) {
        super.onRender(g);
        ArrayList<Integer> breakPoints = getBreakpoints();
        for(int i = 0; i < breakPoints.size()-1; i++){
            g.drawString(text.substring(breakPoints.get(i), breakPoints.get(i+1)), new Vector(0, getYOffs(i)));
        }
    }
}
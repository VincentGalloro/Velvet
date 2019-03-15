package core.main.ui.elements.impl;

import core.main.VGraphics;
import core.main.structs.Vector;
import core.main.ui.elements.BasicTextable;
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
    
    private ArrayList<Integer> getBreakpoints(){
        ArrayList<Integer> points = new ArrayList<>();
        points.add(0);
        int last = 0, length = 1;
        while(last+length < text.length()){
            while(last+length < text.length() && fontMetrics.stringWidth(text.substring(last, last+length+1)) <= width){ 
                length++; 
            }
            last += length;
            points.add(last);
            length = 1;
        }
        return points;
    }
    
    public Vector getSize() { 
        int lineCount = Math.max(getBreakpoints().size()-1, 1);
        return new Vector(width, (fontMetrics.getHeight() + sep)*lineCount - sep); 
    }
    
    public void render(VGraphics g) {
        g.setColor(color);
        g.setFont(font);
        fontMetrics = g.getGraphics().getFontMetrics();
        ArrayList<Integer> breakPoints = getBreakpoints();
        for(int i = 0; i < breakPoints.size()-1; i++){
            g.drawString(text.substring(breakPoints.get(i), breakPoints.get(i+1)), new Vector(0, fontMetrics.getAscent() + (fontMetrics.getHeight() + sep)*i));
        }
    }
}
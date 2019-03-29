package core.main.smooth;

import core.main.smooth.motion.MotionFactory;
import java.awt.Color;

public class SmoothColor {
    
    private Color color, smooth;
    private MotionFactory motionFactory;
    private MotionColor motionColor;
    
    public SmoothColor(Color color){
        this.color = color;
        this.smooth = color;
    }
    
    public SmoothColor(){}
    
    public void setMotionFactory(MotionFactory motionFactory){
        this.motionFactory = motionFactory;
    }
    
    public void setColor(Color color){ 
        this.color = color;  
        createMotion();
    }
    
    public void setSmooth(Color color){ 
        this.smooth = color; 
        createMotion();
    }
    
    private void createMotion(){
        if(motionFactory != null){
            motionColor = new MotionColor(smooth, color, motionFactory.create());
        }
    }
    
    public void overrideColor(Color color){
        this.color = color;
        this.smooth = color;
        motionColor = null;
    }
    
    public void update(){
        if(motionColor != null){
            smooth = motionColor.update();
            if(atTarget()){ 
                smooth = new Color(color.getRGB());
                motionColor = null; 
            }
        }
    }
    
    public Color getColor(){ return color; }
    public Color getSmooth(){ return smooth; }
    public boolean atTarget(){ return motionColor==null ? true : motionColor.atTarget(); }
}
package core.main.smooth;

import core.main.smooth.motion.Motion;
import core.main.smooth.motion.MotionFactory;
import java.awt.Color;

public class SmoothColor {
    
    private Color color, smooth;
    private MotionFactory motionFactory;
    private MotionColor motionColor;
    
    public SmoothColor(Color color, MotionFactory motionFactory){
        this.color = color;
        this.smooth = color==null ? Color.WHITE : color;
        this.motionFactory = motionFactory;
    }
    public SmoothColor(MotionFactory motionFactory){ this(null, motionFactory); }
    public SmoothColor(){ this(Motion.linear(60)); }
    
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
            if(color == null || smooth == null){ motionColor = null; }
            else{
                motionColor = new MotionColor(smooth, color, motionFactory.create());
            }
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
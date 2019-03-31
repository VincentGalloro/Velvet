
package core.main.smooth;

import core.main.smooth.motion.MotionFactory;

public class SmoothScalar {
    
    private double value, smoothValue;
    private MotionFactory motionFactory;
    private MotionScalar motionScalar;
    
    public SmoothScalar(double value, MotionFactory motionFactory){
        this.value = value;
        this.smoothValue = value;
        this.motionFactory = motionFactory;
    }
    
    public SmoothScalar(MotionFactory motionFactory){
        this.motionFactory = motionFactory;
        this.smoothValue = 0;
    }
    
    public void setMotionFactory(MotionFactory motionFactory){
        this.motionFactory = motionFactory;
    }
    
    public void setValue(double value){ 
        this.value = value;
        motionScalar = new MotionScalar(smoothValue, value, motionFactory.create());
    }
    
    public void override(double value){
        this.value = value;
        this.smoothValue = value;
        motionScalar=null;
    }
    
    public void update(){
        if(motionScalar != null){
            smoothValue = motionScalar.update();
            if(atTarget()){ 
                smoothValue = value;
                motionScalar = null; 
            }
        }
    }
    
    public double getValue(){ return value; }
    public double getSmooth(){ return smoothValue; }
    public boolean atTarget(){ return motionScalar==null ? true : motionScalar.atTarget(); }
}


package core.main.smooth.motion;

public abstract class MotionFactory {
    
    protected final int timeSteps;
    
    public MotionFactory(int timeSteps){
        this.timeSteps = timeSteps;
    }
    
    public abstract SmoothMotion create();
}

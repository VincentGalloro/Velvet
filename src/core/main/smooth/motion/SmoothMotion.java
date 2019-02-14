
package core.main.smooth.motion;

public abstract class SmoothMotion {
    
    private int currentTime, timeSteps;
    protected double deltaTime;
    
    public SmoothMotion(int timeSteps){
        this.timeSteps = timeSteps;
    }
    
    public final void update() {
        currentTime++;
        if(currentTime > timeSteps){ currentTime = timeSteps; }
        deltaTime = currentTime / (double)timeSteps;
    }
    
    public abstract double getDelta();

    public final boolean atTarget() { return currentTime >= timeSteps; }
}

package core.main.smooth.motion;

public class LinearMotion extends SmoothMotion{
    
    public LinearMotion(int timeSteps){
        super(timeSteps);
    }

    public double getDelta() {
        return this.deltaTime;
    }
}
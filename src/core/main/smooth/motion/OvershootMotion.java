package core.main.smooth.motion;

public class OvershootMotion extends SmoothMotion{
    
    public OvershootMotion(int timeSteps){
        super(timeSteps);
    }

    public double getDelta(){
        return 1 - Math.pow(Math.exp(-deltaTime), 5) * Math.cos(11 * deltaTime);
    }
}
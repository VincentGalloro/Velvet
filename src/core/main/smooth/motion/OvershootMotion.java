package core.main.smooth.motion;

public class OvershootMotion extends SmoothMotion{
    
    public static class Factory extends MotionFactory{

        public Factory(int timeSteps) {
            super(timeSteps);
        }
        
        public OvershootMotion create() {
            return new OvershootMotion(timeSteps);
        }
    }
    
    public OvershootMotion(int timeSteps){
        super(timeSteps);
    }

    public double getDelta(){
        return 1 - Math.pow(Math.exp(-deltaTime), 5) * Math.cos(11 * deltaTime);
    }
}
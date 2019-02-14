package core.main.smooth.motion;

public class LinearMotion extends SmoothMotion{
    
    public static class Factory extends MotionFactory{

        public Factory(int timeSteps) {
            super(timeSteps);
        }
        
        public LinearMotion create() {
            return new LinearMotion(timeSteps);
        }
    }
    
    public LinearMotion(int timeSteps){
        super(timeSteps);
    }

    public double getDelta() {
        return this.deltaTime;
    }
}
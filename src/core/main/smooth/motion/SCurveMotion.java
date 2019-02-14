
package core.main.smooth.motion;

public class SCurveMotion extends SmoothMotion{

    public static class Factory extends MotionFactory{

        public Factory(int timeSteps) {
            super(timeSteps);
        }
        
        public SCurveMotion create() {
            return new SCurveMotion(timeSteps);
        }
    }
    
    public SCurveMotion(int timeSteps) {
        super(timeSteps);
    }
    
    public double getDelta(){
        if(deltaTime >= 0.5){ return 1 - 8*Math.pow(1-deltaTime, 4); }
        return 8 * Math.pow(deltaTime, 4);
    }
}

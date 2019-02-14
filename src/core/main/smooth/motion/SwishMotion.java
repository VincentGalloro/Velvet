
package core.main.smooth.motion;

public class SwishMotion extends SmoothMotion{
 
    public static class Factory extends MotionFactory{

        public Factory(int timeSteps) {
            super(timeSteps);
        }
        
        public SwishMotion create() {
            return new SwishMotion(timeSteps);
        }
    }
    
    //Formula: (1 - e^-sx) / (1 - e^-s)
    //let A = 1 / (1 - e^-s), the new formula is A * (1 - e^-sx)
    
    //set up constants to avoid calculation
    private static final double SHARPNESS = 5.0; //this can be changed freely
    private static final double A = 1 / (1 - Math.exp(-SHARPNESS));
    
    public SwishMotion(int timeSteps){
        super(timeSteps);
    }

    public double getDelta(){
        return A * (1 - Math.exp(-SHARPNESS * deltaTime));
    }
}

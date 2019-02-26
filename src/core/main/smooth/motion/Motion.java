package core.main.smooth.motion;

public class Motion {

    public static MotionFactory linear(int timeSteps){ return new LinearMotion.Factory(timeSteps); }
    public static MotionFactory overshoot(int timeSteps){ return new OvershootMotion.Factory(timeSteps); }
    public static MotionFactory scruve(int timeSteps){ return new SCurveMotion.Factory(timeSteps); }
    public static MotionFactory swish(int timeSteps){ return new SwishMotion.Factory(timeSteps); }
}
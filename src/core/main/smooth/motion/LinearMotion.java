package core.main.smooth.motion;

import core.main.structs.Vector;

public class LinearMotion implements ISmoothMotion{

    private Vector start, end;
    private int currentTime, timeSteps;
    
    public LinearMotion(Vector start, Vector end, int timeSteps){
        this.start = start;
        this.end = end;
        this.timeSteps = timeSteps;
    }
    
    public Vector update() {
        currentTime++;
        if(currentTime > timeSteps){ currentTime = timeSteps; }
        double delta = currentTime/(double)timeSteps;
        return start.multiply(1-delta).add(end.multiply(delta));
    }

    public boolean atTarget() { return currentTime >= timeSteps; }

}
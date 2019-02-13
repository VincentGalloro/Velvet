
package core.main.smooth.motion;

import core.main.structs.Vector;

public class SCurveMotion implements ISmoothMotion{

    private Vector start, end;
    private int currentTime, timeSteps;
    
    public SCurveMotion(Vector start, Vector end, int timeSteps){
        this.start = start;
        this.end = end;
        this.timeSteps = timeSteps;
    }
    
    public Vector update() {
        currentTime++;
        if(currentTime > timeSteps){ currentTime = timeSteps; }
        
        double deltaTime = currentTime/(double)timeSteps;
        double delta = 8 * Math.pow(deltaTime, 4);
        if(deltaTime >= 0.5){ delta = 1 - 8*Math.pow(1-deltaTime, 4); }
        
        return start.multiply(1-delta).add(end.multiply(delta));
    }

    public boolean atTarget() { return currentTime >= timeSteps; }
}

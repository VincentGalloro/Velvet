
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
        double delta = 2 * deltaTime*deltaTime;
        if(deltaTime >= 0.5){ delta = 1 - 2*Math.pow(deltaTime-1, 2); }
        
        return start.multiply(1-delta).add(end.multiply(delta));
    }

    public boolean atTarget() { return currentTime >= timeSteps; }
}

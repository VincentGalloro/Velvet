package velvet.ui.boundsprocessors.smooth.motion.impl;

import velvet.ui.boundsprocessors.smooth.motion.Motion;

public class EaseInAndOutMotion implements Motion {

    //recommended between 2 and 4
    private final double sharpness;

    //pre-baked calculations
    private final double factor;

    public EaseInAndOutMotion(double sharpness) {
        this.sharpness = sharpness;
        this.factor = Math.pow(0.5, 1 - sharpness);
    }
    public EaseInAndOutMotion(){ this(3.6); }

    public double getDelta(double delta) {
        if(delta <= 0.5){
            return factor * Math.pow(delta, sharpness);
        }
        return 1 - factor * Math.pow(1-delta, sharpness);
    }
}

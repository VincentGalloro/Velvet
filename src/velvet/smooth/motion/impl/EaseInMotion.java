package velvet.smooth.motion.impl;

import velvet.smooth.motion.Motion;

public class EaseInMotion implements Motion {

    //recommended between 3-7
    private final double sharpness;

    //pre-baked calculations
    private final double factor;

    public EaseInMotion(double sharpness) {
        this.sharpness = sharpness;
        this.factor = 1 / (1 - Math.exp(-sharpness));
    }
    public EaseInMotion(){ this(5); }

    public double getDelta(double delta) {
        return factor * (1 - Math.exp(-sharpness * delta));
    }
}

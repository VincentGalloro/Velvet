package core.main.smooth;

import core.main.smooth.motion.SmoothMotion;
import java.awt.Color;

public class MotionColor {
    
    private final Color start, end;
    private final SmoothMotion motion;
    
    public MotionColor(Color start, Color end, SmoothMotion motion){
        this.start = start;
        this.end = end;
        this.motion = motion;
    }
    
    public Color update(){
        motion.update();
        double delta = motion.getDelta();
        int r = (int)(start.getRed() * (1-delta) + end.getRed() * delta);
        int g = (int)(start.getGreen() * (1-delta) + end.getGreen() * delta);
        int b = (int)(start.getBlue() * (1-delta) + end.getBlue() * delta);
        int a = (int)(start.getAlpha() * (1-delta) + end.getAlpha() * delta);
        return new Color(r, g, b, a);
    }
    
    public boolean atTarget(){ return motion.atTarget(); }
}

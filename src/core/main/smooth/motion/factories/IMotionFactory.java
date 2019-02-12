
package core.main.smooth.motion.factories;

import core.main.smooth.motion.ISmoothMotion;
import core.main.structs.Vector;

public interface IMotionFactory {
    
    public ISmoothMotion create(Vector start, Vector end);
}

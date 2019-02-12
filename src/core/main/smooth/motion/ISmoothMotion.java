
package core.main.smooth.motion;

import core.main.structs.Vector;

public interface ISmoothMotion {
    
    public Vector update();
    public boolean atTarget();
}

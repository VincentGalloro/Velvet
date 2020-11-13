package velvet.game

import velvet.util.types.spatial.Bounds
import velvet.util.types.spatial.Size
import velvet.util.types.spatial.Vector

typealias CameraBoundsRenderMode = (Bounds, Size)->Bounds

//Match bounds exactly
class StretchToFitRenderMode : CameraBoundsRenderMode {
    override fun invoke(p1: Bounds, p2: Size) = p1
}

//Add padding on the smaller dimension to avoid break aspect ratio
class PadToFitRenderMode : CameraBoundsRenderMode {
    override fun invoke(p1: Bounds, p2: Size)
            = p1.setSize((p2.vector*(p1.size/p2).max).toArea(), Vector.HALF)
}
package velvet.ui.boundsprocessors.layouts

import velvet.ui.boundsprocessors.BoundsProcessor
import velvet.util.types.spatial.Bounds

class ToggleableLayout(private val boundsProcessor: BoundsProcessor,
                       var enabled: Boolean = true) : BoundsProcessor{

    override fun invoke(it: Bounds, index: Int): Bounds {
        if(enabled) return boundsProcessor(it, index)
        return it
    }
}
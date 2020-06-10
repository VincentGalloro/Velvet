package velvet.ui.boundsprocessors

import velvet.util.types.spatial.Bounds

typealias BoundsProcessor = (it: Bounds, index: Int)-> Bounds
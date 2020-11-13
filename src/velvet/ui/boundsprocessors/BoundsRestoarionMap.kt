package velvet.ui.boundsprocessors

import velvet.ui.UINode
import velvet.util.types.spatial.Bounds

class BoundsRestorationMap<T, U : UINode>(private val keyMapper: (U)->T) {

    private val bounds: MutableMap<T, Bounds> = mutableMapOf()

    fun store(uiNode: U){
        bounds[keyMapper(uiNode)] = uiNode.bounds
    }
    fun store(uiNodes: List<U>) = uiNodes.forEach { store(it) }
    fun store(uiNode: UINode, key: T){
        bounds[key] = uiNode.bounds
    }

    fun restore(uiNode: U){
        bounds[keyMapper(uiNode)]?.let { uiNode.bounds = it }
    }
    fun restore(uiNodes: List<U>) = uiNodes.forEach { restore(it) }
    fun restore(uiNode: UINode, key: T){
        bounds[key]?.let { uiNode.bounds = it }
    }
}
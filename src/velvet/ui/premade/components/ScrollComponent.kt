package velvet.ui.premade.components

import velvet.ui.UINode
import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.ui.boundsprocessors.smooth.actuators.impl.DoubleSwishActuator

class ScrollComponent: BasicComponent() {

    var targetScroll: Int = 0
    var scroll: Double = 0.0
    var scrollRate: Int = 1
    private val scrollActuator: Actuator<Double> = DoubleSwishActuator()

    var visibleNodes: Set<Int> = setOf()
        set(value) {
            onItemExit?.let { (field - value).forEach(it) }
            onItemEnter?.let { (value - field).forEach(it) }
            field = value
        }

    var onItemEnter: ((Int)->Unit)? = null
    var onItemExit: ((Int)->Unit)? = null

    init{
        uiEventListener.onMouseScroll = { targetScroll += it * scrollRate }
    }

    private fun updateVisible(uiNode: UINode){
        visibleNodes = uiNode.subNodes.mapIndexedNotNull { index, subNode ->
            if(uiNode.bounds.intersects(subNode.bounds)) index else null
        }.toSet()
    }

    override fun preUpdate(uiNode: UINode) {
        scroll = scrollActuator(scroll, targetScroll.toDouble())
        updateVisible(uiNode)
    }
}
package velvet.ui.components.functional

import velvet.ui.UINode
import velvet.ui.boundsprocessors.smooth.actuators.Actuator
import velvet.ui.boundsprocessors.smooth.actuators.impl.DoubleSwishActuator
import velvet.ui.components.BasicComponent
import java.awt.event.KeyEvent

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
        uiEventListener.onMouseWheelScrolled = { it, _ -> targetScroll += it.amount * scrollRate }
        uiEventListener.onKeyPressed = { it, _ ->
            if(it.code == KeyEvent.VK_UP){
                targetScroll -= scrollRate
            }
            else if(it.code == KeyEvent.VK_DOWN){
                targetScroll += scrollRate
            }
        }
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
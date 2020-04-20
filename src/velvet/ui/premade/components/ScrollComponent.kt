package velvet.ui.premade.components

import velvet.smooth.actuators.Actuator
import velvet.smooth.actuators.impl.DoubleSwishActuator
import velvet.ui.UIEventListener
import velvet.ui.UINode
import velvet.ui.premade.layouts.Layout

class ScrollComponent(var layout: Layout) : UIComponent{

    var targetScroll: Int = 0
    var scroll: Double = 0.0
    var scrollRate: Int = 1
    private val scrollActuator: Actuator<Double> = DoubleSwishActuator()

    var visibleNodes: Set<Int> = setOf()
    var onItemEnter: ((Int)->Unit)? = null
    var onItemExit: ((Int)->Unit)? = null

    override val uiEventListener = UIEventListener()

    init{
        uiEventListener.onMouseScroll = { targetScroll += it * scrollRate }
    }

    private fun updateVisible(uiNode: UINode){
        val newVisible = layout.getVisibleRange(uiNode.bounds, scroll).toHashSet()
        (visibleNodes - newVisible).forEach {
            onItemExit?.invoke(it)
            uiNode.subNodes.getOrNull(it)?.enabled = false
        }
        (newVisible - visibleNodes).forEach {
            onItemEnter?.invoke(it)
            uiNode.subNodes.getOrNull(it)?.enabled = true
        }
        visibleNodes = newVisible
    }

    override fun postUpdate(uiNode: UINode){
        scroll = scrollActuator.step(scroll, targetScroll.toDouble())
        updateVisible(uiNode)
    }
}
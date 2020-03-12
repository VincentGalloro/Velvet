package velvet.vcontainer.premade

import velvet.smooth.actuators.Actuator
import velvet.smooth.actuators.impl.DoubleSwishActuator
import velvet.structs.Vector
import velvet.vcontainer.interact.UINode
import velvet.vcontainer.interact.UINodeImpl
import velvet.vcontainer.premade.layouts.Layout
import kotlin.math.floor

class ScrollableListNode private constructor(var layout: Layout,
                                             private val uiNode: UINode) :
        UINode by uiNode {

    constructor(layout: Layout) : this(layout, UINodeImpl())

    var targetScroll: Int = 0
    var scroll: Double = 0.0
    private val scrollActuator: Actuator<Double> = DoubleSwishActuator()
    private var scrollOffset: Vector = Vector()

    init{
        uiEventListener.onMouseScroll = { targetScroll += it }
    }

    override fun update(){
        uiNode.update()
        scroll = scrollActuator.step(scroll, targetScroll.toDouble())

        calculateScrollOffset()
    }

    private fun calculateScrollOffset(){
        val roundedScroll = floor(scroll).toInt()
        val fractionalScroll = scroll - roundedScroll

        val prevPos = layout.getBounds(roundedScroll, bounds).getPos(Vector())
        val nextPos = layout.getBounds(roundedScroll + 1, bounds).getPos(Vector())

        val scrollPoint = prevPos * (1 - fractionalScroll) + nextPos * fractionalScroll
        scrollOffset = layout.getBounds(0, bounds).getPos(Vector()) - scrollPoint
    }

    fun loadNodes(nodes: List<UINode>){
        subNodes.clear()
        subNodes.addAll(nodes)

        subNodes.forEachIndexed { index, uiNode ->
            uiNode.boundsGenerator = { layout.getBounds(index, bounds).globalMove(scrollOffset) }
        }
    }
}
package velvet.ui.premade

import velvet.ui.UINode
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.components.graphical.DecayComponent
import velvet.util.types.VColor
import velvet.util.types.spatial.Vector

class DecayingUINode<T : UINode>(val uiNode: T, val decayComponent: DecayComponent)

class LogPanelNode : UINode(){

    private val listNode = ListNode()

    var listNodeLayout = Layout.new().padCenter(20.0).columnList().scaledHeight(0.25, sepHeight = 10.0).noScroll()

    init{
        add(listNode, Layout.new())
    }

    fun log(text: String,
            fillColor: VColor = VColor.WHITE,
            initialTime: Int = 300,
            fadeTime: Int = 60): DecayingUINode<OutlinedTextNode>{
        val node = OutlinedTextNode(text)
        node.squareElement.fillColor = fillColor
        node.squareElement.rounding = 5.0
        node.textElement.setFontResolution(40)
        return log(node, initialTime, fadeTime)
    }

    fun <T: UINode> log(uiNode: T, initialTime: Int = 300, fadeTime: Int = 60): DecayingUINode<T>{
        val decayComponent = DecayComponent(initialTime, fadeTime)
        decayComponent.onDecay = { listNode.remove(uiNode) }

        uiNode.add(decayComponent)
        listNode.add(uiNode, Layout.new().track(
                listNodeLayout,
                boundsInitializer = { it, _ -> it.localMove(Vector(it.size.width, 0))}))

        return DecayingUINode(uiNode, decayComponent)
    }
}
package experiments

import velvet.main.Velvet
import velvet.main.VelvetState
import velvet.ui.boundsprocessors.layouts.Layout
import velvet.ui.components.functional.OnClickComponent
import velvet.ui.components.graphical.SquareHoverComponent
import velvet.ui.premade.DecayingUINode
import velvet.ui.premade.LogPanelNode
import velvet.ui.premade.OutlinedTextNode
import velvet.util.types.VColor
import velvet.util.types.spatial.Size
import velvet.util.types.spatial.Vector

class LogTest(velvetState: VelvetState) : Velvet(velvetState) {

    init{
        val shortButton = OutlinedTextNode("Short")
        val mediumButton = OutlinedTextNode("Medium")
        val longButton = OutlinedTextNode("Long")

        shortButton.squareElement.fillColor = VColor.fromHSB(1/3.0, 0.3, 1.0)
        mediumButton.squareElement.fillColor = VColor.fromHSB(1/6.0, 0.3, 1.0)
        longButton.squareElement.fillColor = VColor.fromHSB(0.0, 0.3, 1.0)

        shortButton.textElement.setFontResolution(40)
        mediumButton.textElement.setFontResolution(40)
        longButton.textElement.setFontResolution(40)

        shortButton.add(SquareHoverComponent(shortButton.squareElement))
        mediumButton.add(SquareHoverComponent(mediumButton.squareElement))
        longButton.add(SquareHoverComponent(longButton.squareElement))

        val logPanel = LogPanelNode()
        var countNode: DecayingUINode<OutlinedTextNode>? = null
        var count = 0

        fun count(){
            count++
            countNode?.let {
                if(it.decayComponent.hasDecayed){
                    countNode = null
                }
                else{
                    it.decayComponent.refresh()
                    it.uiNode.textElement.text = "You've Pressed $count Buttons"
                }
            }
            countNode = countNode ?: logPanel.log("You've Pressed $count Buttons",
                    fillColor = VColor.fromHSB(0.5, 0.3, 1.0))
        }

        shortButton.add(OnClickComponent.left {
            count()
            logPanel.log("Short Pressed", shortButton.squareElement.fillColor ?: VColor.WHITE,
                    initialTime = 100,
                    fadeTime = 30)
        })
        mediumButton.add(OnClickComponent.left {
            count()
            logPanel.log("Medium Pressed", mediumButton.squareElement.fillColor ?: VColor.WHITE)
        })
        longButton.add(OnClickComponent.left {
            count()
            logPanel.log("Long Pressed", longButton.squareElement.fillColor ?: VColor.WHITE,
                    initialTime = 600)
        })

        rootNode.add(shortButton, Layout.of { it, _ -> it.setSize(Vector(200, 50), Vector(0.5, 0.3)) })
        rootNode.add(mediumButton, Layout.of { it, _ -> it.setSize(Vector(200, 50), Vector(0.5, 0.5)) })
        rootNode.add(longButton, Layout.of { it, _ -> it.setSize(Vector(200, 50), Vector(0.5, 0.7)) })
        rootNode.add(logPanel, Layout.new().horizontalSplit().rightWidth(320.0).right())
    }

    override fun onClose() {
    }
}

fun main(){
    Velvet.start({ LogTest(it) }, Size(1500, 832), "Log Test")
}
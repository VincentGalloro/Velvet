package velvet.main

import velvet.ui.UINode
import velvet.ui.components.BasicComponent
import velvet.util.types.VCircle
import velvet.util.types.VColor
import velvet.util.types.VLine
import velvet.util.types.spatial.Area
import velvet.util.types.spatial.Size
import velvet.util.types.spatial.Vector
import java.awt.BasicStroke

class TestVelvet(velvetState: VelvetState) : Velvet(velvetState) {

    private var points: List<Vector> = listOf()

    init{
        rootNode.add(object : BasicComponent(){

            var control = Vector()
            val start = Vector(0.2, 0.2) * size
            val end = Vector(0.2, 0.8) * size
            var va1 = Vector()
            var va2 = Vector()
            var vp = Vector()
            var anim = 0.0

            init{
                uiEventListener.onMouseMoved = { e, _ -> control = e.pos }
            }

            override fun postUpdate(uiNode: UINode) {
                points = (0..101).map { it*0.01 }.map {
                    val a1 = start*(1-it) + control*it
                    val a2 = control*(1-it) + end*it
                    a1*(1-it) + a2*it
                }
                anim = (anim + 0.01) % 2
                val a = if(anim<1) anim else 2-anim

                va1 = start*(1-a) + control*a
                va2 = control*(1-a) + end*a
                vp = va1*(1-a) + va2*a
            }

            override fun postRender(uiNode: UINode, g: VGraphics) {
                g.stroke = BasicStroke(5.toFloat())
                g.color = VColor.BLUE
                g.draw(VLine(start, control))
                g.draw(VLine(control, end))
                g.draw(VLine(va1, va2))
                g.draw(VCircle(va1-10, Area(20)))
                g.draw(VCircle(va2-10, Area(20)))

                g.color = VColor.RED
                g.draw(VCircle(vp-10, Area(20)))

                g.color = VColor.RED
                g.draw(VCircle(control-10, Area(20)))
                for(i in points.indices - (points.size-1)){
                    g.draw(VLine(points[i], points[i+1]))
                }
            }
        })
    }

    override fun onClose() {
    }
}

fun main(){
    Velvet.start({ TestVelvet(it) }, Size(1500, 832), "Test Velvet")
}
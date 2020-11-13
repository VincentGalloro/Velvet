package velvet.main

import velvet.game.graphics.Sprite
import velvet.util.types.VColor
import velvet.util.types.spatial.Vector
import velvet.util.types.VShape
import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.util.*

class VGraphics(private var g: Graphics2D) {

    private val graphicsStack: ArrayList<Graphics2D> = ArrayList()
    private val saves: ArrayList<AffineTransform>

    val baseGraphics: Graphics2D
        get() = g

    var transform: AffineTransform
        get() = g.transform
        set(at) { g.transform = at }

    val fontMetrics: FontMetrics
        get() = g.fontMetrics

    var font: Font
        get() = g.font
        set(value){ g.font = value }

    var composite: Composite
        get() = g.composite
        set(c) { g.composite = c }

    var stroke: Stroke
        get() = g.stroke
        set(value){ g.stroke = value }

    var color: VColor
        get() = VColor.fromJavaColor(g.color)
        set(value){ g.color = value.javaColor }

    init {
        graphicsStack.add(g)
        saves = ArrayList()
    }

    fun save() = saves.add(g.transform)
    fun reset(){ g.transform = saves.removeAt(saves.size - 1) }
    fun getTransformSave(index: Int) = saves[index]

    fun subGraphics(g: Graphics2D) {
        graphicsStack.add(g)
        this.g = g
    }

    fun resetGraphics() {
        graphicsStack.removeAt(graphicsStack.size - 1)
        this.g = graphicsStack.last()
    }

    fun translate(v: Vector) = g.translate(v.x, v.y)
    fun scale(d: Double) = g.scale(d, d)
    fun scale(v: Vector) = g.scale(v.x, v.y)
    fun rotate(a: Double) = g.rotate(a)
    fun rotate(a: Double, p: Vector) = g.rotate(a, p.x, p.y)
    fun transform(at: AffineTransform) = g.transform(at)

    fun resetStroke() { stroke = BasicStroke(1f) }
    fun setRenderingHint(key: RenderingHints.Key, value: Any) = g.setRenderingHint(key, value)

    fun draw(s: VShape) = g.draw(s.javaShape)
    fun fill(s: VShape) = g.fill(s.javaShape)

    fun drawImage(img: BufferedImage) = g.drawImage(img, 0, 0, null)
    fun drawImage(img: BufferedImage, pos: Vector) = g.drawImage(img, pos.x.toInt(), pos.y.toInt(), null)
    fun drawImage(img: BufferedImage, at: AffineTransform) {
        save()
        transform(at)
        drawImage(img)
        reset()
    }

    fun drawSprite(sprite: Sprite) = drawImage(sprite.image)
    fun drawSprite(sprite: Sprite, pos: Vector) = drawImage(sprite.image, pos)
    fun drawSprite(sprite: Sprite, at: AffineTransform) = drawImage(sprite.image, at)

    fun drawString(s: String, pos: Vector) = g.drawString(s, pos.x.toFloat(), pos.y.toFloat())
}
package velvet.main

import velvet.main.game.graphics.Sprite
import velvet.structs.Vector
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

    var composite: Composite
        get() = g.composite
        set(c) { g.composite = c }

    init {
        graphicsStack.add(g)
        saves = ArrayList()
    }

    fun save() {
        saves.add(g.transform)
    }

    fun reset() {
        g.transform = saves.removeAt(saves.size - 1)
    }

    fun getTransformSave(index: Int): AffineTransform {
        return saves[index]
    }

    fun subGraphics(g: Graphics2D) {
        graphicsStack.add(g)
        this.g = g
    }

    fun resetGraphics() {
        graphicsStack.removeAt(graphicsStack.size - 1)
        this.g = graphicsStack[graphicsStack.size - 1]
    }

    fun translate(v: Vector) {
        g.translate(v.x, v.y)
    }

    fun scale(d: Double) {
        g.scale(d, d)
    }

    fun scale(v: Vector) {
        g.scale(v.x, v.y)
    }

    fun rotate(a: Double) {
        g.rotate(a)
    }

    fun rotate(a: Double, p: Vector) {
        g.rotate(a, p.x, p.y)
    }

    fun transform(at: AffineTransform) {
        g.transform(at)
    }

    fun setStroke(s: Stroke) {
        g.stroke = s
    }

    fun resetStroke() {
        g.stroke = BasicStroke(1f)
    }

    fun setColor(c: Color) {
        g.color = c
    }

    fun setFont(f: Font) {
        g.font = f
    }

    fun setRenderingHint(key: RenderingHints.Key, value: Any) {
        g.setRenderingHint(key, value)
    }

    fun draw(s: Shape) {
        g.draw(s)
    }

    fun fill(s: Shape) {
        g.fill(s)
    }

    fun drawImage(img: BufferedImage) {
        g.drawImage(img, 0, 0, null)
    }

    fun drawImage(img: BufferedImage, pos: Vector) {
        g.drawImage(img, pos.x.toInt(), pos.y.toInt(), null)
    }

    fun drawImage(img: BufferedImage, at: AffineTransform) {
        save()
        transform(at)
        drawImage(img)
        reset()
    }

    fun drawSprite(sprite: Sprite) = drawImage(sprite.image)
    fun drawSprite(sprite: Sprite, pos: Vector) = drawImage(sprite.image, pos)
    fun drawSprite(sprite: Sprite, at: AffineTransform) = drawImage(sprite.image, at)

    fun drawString(s: String, pos: Vector){
        g.drawString(s, pos.x.toFloat(), pos.y.toFloat())
    }
}
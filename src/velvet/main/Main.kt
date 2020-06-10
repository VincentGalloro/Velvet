package velvet.main

import velvet.ui.UIEventHandler
import velvet.util.types.spatial.Size
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.IOException
import java.net.URI
import javax.imageio.ImageIO
import javax.swing.JFrame

class Main(levelGenerator: (VelvetState)->Velvet, size: Size, name: String) : Canvas(), Runnable {

    companion object {
        val BACKGROUND_COLOR = Color.WHITE
    }

    private val frame: JFrame
    private val title: String
    private val keyboard: Keyboard
    private val mouse: Mouse
    private val uiEventHandler: UIEventHandler

    private val level: Velvet

    init {
        val d: Dimension = size.dimension
        title = name
        frame = JFrame(name)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isResizable = false
        preferredSize = d
        maximumSize = d
        minimumSize = d
        frame.add(this)
        frame.pack()
        frame.isVisible = true
        frame.setLocationRelativeTo(null)
        Thread(Runnable {
            val URL = "https://raw.githubusercontent.com/VincentGalloro/Velvet/master/Velvet_Icon.png"
            try {
                val icon = ImageIO.read(URI.create(URL).toURL().openStream())
                frame.iconImage = icon
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
        keyboard = Keyboard()
        addKeyListener(keyboard)
        mouse = Mouse()
        addMouseListener(mouse)
        addMouseMotionListener(mouse)
        addMouseWheelListener(mouse)
        val fileDrop = FileDrop()
        frame.dropTarget = fileDrop
        uiEventHandler = UIEventHandler(mouse, keyboard)

        val state = VelvetState(size, mouse, keyboard, uiEventHandler, fileDrop)
        level = levelGenerator(state)

        level.init()
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(windowEvent: WindowEvent) {
                level.onClose()
            }
        })
        start()
    }

    @Synchronized
    fun start() {
        Thread(this).start()
    }

    override fun run() {
        var lastTime = System.nanoTime()
        var secondTimer = System.currentTimeMillis()
        val nsPerTick = 1000000000.0 / 60.0
        var delta = 0.0
        while (true) {
            val now = System.nanoTime()
            delta += (now - lastTime) / nsPerTick
            lastTime = now
            if (System.currentTimeMillis() > secondTimer + 1000) {
                secondTimer = System.currentTimeMillis()
                if (delta > 60) {
                    delta = 60.0
                }
                if (delta >= 10) {
                    frame.title = "$title | WARNING [RUNNING BELOW 60FPS] | Frame Deficit = $delta"
                } else {
                    frame.title = title
                }
            }
            if (delta >= 1) {
                delta -= 1.0
                update()
                render()
            }
        }
    }

    fun update() {
        keyboard.update()
        mouse.update()
        uiEventHandler.update()
        level.update()
    }

    fun render() {
        if (bufferStrategy == null) {
            createBufferStrategy(2)
        }
        val g = bufferStrategy.drawGraphics
        g.color = BACKGROUND_COLOR
        g.fillRect(0, 0, level.size.width, level.size.height)
        (g as Graphics2D).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        level.render(VGraphics(g))
        g.dispose()
        bufferStrategy.show()
    }

}
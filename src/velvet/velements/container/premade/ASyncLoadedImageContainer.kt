package velvet.velements.container.premade

import velvet.multithreading.Bot
import velvet.velements.container.VContainer
import velvet.velements.impl.ImageElement
import velvet.velements.impl.SquareElement
import java.awt.Color
import java.awt.image.BufferedImage

open class ASyncLoadedImageContainer: VContainer() {

    val imageElement = ImageElement()
    val squareElement = SquareElement(fillColor = Color.WHITE)

    init{
        vElement = squareElement
        subContainers.add(VContainer(imageElement) { bounds.fixRatioElement(it.vElement) })
    }

    fun loadImage(loadBot: Bot<*>, loader: ()->BufferedImage) {
        if(imageElement.image == null) { //check for content before adding the job
            loadBot.addJob {
                if (imageElement.image == null) { //check for content before executing the job
                    imageElement.image = loader()
                }
            }
        }
    }

    fun reloadImage(loadBot: Bot<*>, loader: ()->BufferedImage) {
        loadBot.addJob { imageElement.image = loader() }
    }

    fun unload() {
        imageElement.image = null
    }
}
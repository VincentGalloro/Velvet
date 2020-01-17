package velvet.velements.container.premade

import velvet.main.game.graphics.Sprite
import velvet.multithreading.Bot
import velvet.velements.container.VContainer
import velvet.velements.impl.SpriteElement
import velvet.velements.impl.SquareElement
import java.awt.Color
import java.io.File

open class ASyncLoadedSpriteContainer: VContainer() {

    val spriteElement = SpriteElement()
    val squareElement = SquareElement(fillColor = Color.WHITE)

    init{
        vElement = squareElement
        subContainers.add(VContainer(spriteElement) { bounds.fixRatioElement(it.vElement) })
    }

    fun <T> loadSprite(loadBot: Bot<T>, loader: (T)->Sprite) {
        if(spriteElement.sprite == null) { //check for content before adding the job
            loadBot.addJob {
                if (spriteElement.sprite == null) { //check for content before executing the job
                    spriteElement.sprite = loader(it)
                }
            }
        }
    }

    fun <T> reloadSprite(loadBot: Bot<T>, loader: (T)->Sprite) {
        loadBot.addJob { spriteElement.sprite = loader(it) }
    }

    fun loadSprite(loadBot: Bot<*>, file: File) = loadSprite(loadBot){ Sprite.loadSprite(file) }
    fun reloadSprite(loadBot: Bot<*>, file: File) = reloadSprite(loadBot){ Sprite.loadSprite(file) }

    fun unload() {
        spriteElement.sprite = null
    }
}
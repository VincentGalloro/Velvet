package velvet.vcontainer.premade

import velvet.main.game.graphics.Sprite
import velvet.multithreading.Bot
import velvet.vcontainer.interact.TrackedVContainer
import velvet.vcontainer.interact.UINode
import velvet.vcontainer.interact.UINodeImpl
import velvet.vcontainer.velement.SpriteElement
import velvet.vcontainer.velement.SquareElement
import java.awt.Color

class ASyncLoadedSpriteNode: UINode by UINodeImpl() {

    val squareElement = SquareElement(fillColor = Color.WHITE)
    val spriteElement = SpriteElement()

    init{
        containers.add(TrackedVContainer(squareElement){ bounds })
        containers.add(TrackedVContainer(spriteElement, true) { bounds })
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

    fun unload() {
        spriteElement.sprite = null
    }
}
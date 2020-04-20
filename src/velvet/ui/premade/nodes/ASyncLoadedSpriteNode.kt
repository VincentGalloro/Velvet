package velvet.ui.premade.nodes

import velvet.main.game.graphics.Sprite
import velvet.multithreading.Bot
import velvet.ui.UINode

class ASyncLoadedSpriteNode {

    val outlinedSpriteNode = OutlinedSpriteNode()
    val uiNode: UINode
        get() = outlinedSpriteNode.uiNode

    fun <T> loadSprite(loadBot: Bot<T>, loader: (T)->Sprite) {
        if(outlinedSpriteNode.sprite == null) { //check for content before adding the job
            loadBot.addJob {
                if (outlinedSpriteNode.sprite == null) { //check for content before executing the job
                    outlinedSpriteNode.sprite = loader(it)
                }
            }
        }
    }

    fun <T> reloadSprite(loadBot: Bot<T>, loader: (T)->Sprite) {
        loadBot.addJob { outlinedSpriteNode.sprite = loader(it) }
    }

    fun unload() {
        outlinedSpriteNode.sprite = null
    }
}
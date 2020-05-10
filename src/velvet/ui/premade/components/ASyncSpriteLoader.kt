package velvet.ui.premade.components

import velvet.main.game.graphics.Sprite
import velvet.multithreading.Bot
import velvet.ui.vcontainer.velements.SpriteElement

class ASyncSpriteLoader(private val spriteElement: SpriteElement){

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
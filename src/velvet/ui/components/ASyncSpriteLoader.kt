package velvet.ui.components

import velvet.game.graphics.Sprite
import velvet.multithreading.Bot
import velvet.ui.velements.SpriteElement

class ASyncSpriteLoader(private val spriteElement: SpriteElement){

    fun loadSprite(loadBot: Bot, loader: ()-> Sprite) {
        if(spriteElement.sprite == null) { //check for content before adding the job
            loadBot.addJob {
                if (spriteElement.sprite == null) { //check for content before executing the job
                    spriteElement.sprite = loader()
                }
            }
        }
    }

    fun reloadSprite(loadBot: Bot, loader: ()-> Sprite) {
        loadBot.addJob { spriteElement.sprite = loader() }
    }

    fun unload() {
        spriteElement.sprite = null
    }
}
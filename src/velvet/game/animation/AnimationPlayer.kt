package velvet.game.animation

import velvet.game.graphics.Sprite

class AnimationPlayer {

    var animation: Animation? = null
        set(value){
            field = value
            value?.startedHandler?.invoke(this)
        }

    var frame: Double = 0.0
    val sprite: Sprite?
        get() = animation?.track?.getSprite(frame)

    fun update() {
        animation?.let {
            frame += it.speed
            it.updateHandler(this)
            if(!it.track.validFrame(frame)) it.endedHandler(this)
        }
    }
}
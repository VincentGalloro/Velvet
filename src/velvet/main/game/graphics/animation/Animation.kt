package velvet.main.game.graphics.animation

import velvet.main.game.graphics.Sprite
import velvet.main.game.graphics.Track

class Animation(val track: Track,
                val speed: Double,
                var startedHandler: (AnimationPlayer)->Unit,
                var updateHandler: (AnimationPlayer)->Unit,
                var endedHandler: (AnimationPlayer)->Unit){

    companion object{

        fun basicAnimation(track: Track, speed: Double = 0.1)
                = Animation(track, speed, { it.frame = 0.0 }, {}, {})
        fun basicLoopingAnimation(track: Track, speed: Double = 0.1)
                = Animation(track, speed, { it.frame = 0.0 }, {}, { it.frame %= track.frameCount })
        fun basicStaticAnimation(sprite: Sprite)
                = Animation(Track(listOf(sprite)), 0.0, {}, {}, {})
    }
}
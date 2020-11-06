package velvet.game.graphics

class Track private constructor(val sprites: List<Sprite>) {

    companion object{

        operator fun invoke(sprites: List<Sprite>): Track {
            if(sprites.isEmpty()){ throw IllegalArgumentException("Sprite list cannot be empty") }
            return Track(sprites)
        }
    }

    val frameCount = sprites.size

    fun validFrame(frame: Double) = frame >= 0 && frame < frameCount

    fun getSprite(frame: Double): Sprite {
        if(frame < 0){ return sprites.first() }
        if(frame >= frameCount){ return sprites.last() }
        return sprites[frame.toInt()]
    }
}
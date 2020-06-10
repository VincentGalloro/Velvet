package velvet.util

class StopWatch(private val interval: Int, private val action: ()->Unit) {

    private var timer = 0

    fun update(){
        timer++
        if(timer >= interval){
            timer = 0
            action()
        }
    }

    fun reset(){ timer = 0 }
}
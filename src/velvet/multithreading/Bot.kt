package velvet.multithreading

class Bot<out T>(private val tInitializer: ()->T,
                 private val externalQueue: SynchronizedQueue<(T)->Unit>? = null){

    private val jobQueue = SynchronizedQueue<(T)->Unit>()
    private val lowPriorityJobQueue = SynchronizedQueue<(T)->Unit>()

    var idle = false
        @Synchronized get
        @Synchronized set

    private var kill = false
    fun forceKill(){
        kill = true
    }

    init{
        Thread(::run).start()
    }

    private fun run() {
        val t = tInitializer()

        while (!kill) {
            val job = jobQueue.pop() ?: externalQueue?.pop() ?: lowPriorityJobQueue.pop()

            try {
                if(job == null) idle = true
                else{
                    idle = false
                    job.invoke(t)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addJob(job: (T)->Unit) = jobQueue.add(job)
    fun addLowPriorityJob(job: (T)->Unit) = lowPriorityJobQueue.add(job)
}

package velvet.multithreading

class Bot(private val externalQueue: SynchronizedQueue<()->Unit>? = null){

    private val jobQueue = SynchronizedQueue<()->Unit>()
    private val lowPriorityJobQueue = SynchronizedQueue<()->Unit>()

    var idle = false
        @Synchronized get
        @Synchronized set

    private var kill = false
    var isShutdown = false
        @Synchronized get
        @Synchronized private set

    fun forceKill(){
        kill = true
    }

    init{
        Thread(::run).start()
    }

    private fun run() {
        while (!kill) {
            val job = jobQueue.pop() ?: externalQueue?.pop() ?: lowPriorityJobQueue.pop()

            try {
                if(job == null){
                    idle = true
                    Thread.sleep(200)
                }
                else{
                    idle = false
                    job()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        isShutdown = true
    }

    fun addJob(job: ()->Unit) = jobQueue.add(job)
    fun addLowPriorityJob(job: ()->Unit) = lowPriorityJobQueue.add(job)
}

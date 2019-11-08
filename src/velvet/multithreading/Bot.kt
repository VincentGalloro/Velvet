package velvet.multithreading

abstract class Bot<T>(private val externalQueue: SynchronizedQueue<(T)->Unit>? = null){

    private val jobQueue = SynchronizedQueue<(T)->Unit>()
    private val lowPriorityJobQueue = SynchronizedQueue<(T)->Unit>()

    var idle = false
        @Synchronized get
        @Synchronized set

    private var kill = false
    open fun shutdown(){ kill = true }

    protected abstract val invokingArg: T

    fun start(){
        Thread(::run).start()
    }

    private fun run() {
        while (!kill) {
            val job = jobQueue.pop() ?: externalQueue?.pop() ?: lowPriorityJobQueue.pop()

            try {
                if(job == null){ idle = true }
                else{
                    idle = false
                    job.invoke(invokingArg)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addJob(job: (T)->Unit) = jobQueue.add(job)
    fun addLowPriorityJob(job: (T)->Unit) = lowPriorityJobQueue.add(job)
}

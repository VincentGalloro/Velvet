package velvet.automation

abstract class Bot<T>(private val externalQueue: SynchronizedQueue<(T)->Unit>? = null){

    private val jobQueue = SynchronizedQueue<(T)->Unit>()
    private val lowPriorityJobQueue = SynchronizedQueue<(T)->Unit>()

    private var kill = false
    open val shutdown = { kill = true }

    protected abstract val invokingArg: T

    fun start(){
        Thread(::run).start()
    }

    private fun run() {
        while (!kill) {
            val job = jobQueue.pop() ?: externalQueue?.pop() ?: lowPriorityJobQueue.pop()

            try {
                job?.invoke(invokingArg)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addJob(job: (T)->Unit) = jobQueue.add(job)
    fun addLowPriorityJob(job: (T)->Unit) = lowPriorityJobQueue.add(job)
}

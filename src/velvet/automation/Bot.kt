package velvet.automation

open class Bot(private val externalQueue: JobQueue? = null){

    private val jobQueue = JobQueue()
    private val lowPriorityJobQueue = JobQueue()

    private var kill = false

    init {
        Thread(::run).start()
    }

    open val shutdown = { kill = true }

    private fun run() {
        while (!kill) {
            val job = jobQueue.popJob() ?: externalQueue?.popJob() ?: lowPriorityJobQueue.popJob()

            try {
                job?.invoke()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addJob(job: ()->Unit) = jobQueue.addJob(job)
    fun addLowPriorityJob(job: ()->Unit) = lowPriorityJobQueue.addJob(job)
}

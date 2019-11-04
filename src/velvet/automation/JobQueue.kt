package velvet.automation

import java.util.*

class JobQueue {

    private val jobs: LinkedList<() -> Unit> = LinkedList()

    @Synchronized
    fun popJob(): (() -> Unit)? {
        if (jobs.isEmpty()) { return null; }
        return jobs.pop()
    }

    @Synchronized
    fun addJob(job: () -> Unit) = jobs.add(job)
}

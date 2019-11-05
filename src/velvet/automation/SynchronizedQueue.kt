package velvet.automation

import java.util.*

open class SynchronizedQueue<T> {

    private val jobs: LinkedList<T> = LinkedList()

    @Synchronized
    fun pop(): T? {
        if (jobs.isEmpty()) { return null; }
        return jobs.pop()
    }

    @Synchronized
    fun add(job: T){
        jobs.add(job)
    }

    @Synchronized
    fun isEmpty(): Boolean{
        return jobs.isEmpty()
    }
}
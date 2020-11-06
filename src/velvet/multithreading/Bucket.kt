package velvet.multithreading

class Bucket<T>(private val processor: (T)->Unit) {

    private val jobs = SynchronizedQueue<T>()

    fun add(item: T) {
        jobs.add(item)
    }

    fun processJob(){
        jobs.pop()?.let(processor)
    }
}
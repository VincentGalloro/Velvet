package velvet.multithreading

class SynchronizedSolution<T> {

    private var solution: T? = null

    @Synchronized
    fun hasSolution() = solution != null

    @Synchronized
    fun sendSolution(solution: T){ this.solution = solution }

    @Synchronized
    fun takeSolution(): T?{
        val out = solution
        solution = null
        return out
    }
}
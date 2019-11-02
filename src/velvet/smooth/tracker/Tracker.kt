package velvet.smooth.tracker

interface Tracker<T> {

    fun step(current: T, target: T): T
}

package velvet.multithreading

class SynchronizedArrayList<T> {

    private val items = ArrayList<T>()

    @Synchronized
    fun get(index: Int): T {
        return items[index]
    }

    @Synchronized
    fun add(item: T){
        items.add(item)
    }

    @Synchronized
    fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    @Synchronized
    fun remove(item: T){
        items.remove(item)
    }

    @Synchronized
    fun forEach(consumer: (T)->Unit){
        items.forEach(consumer)
    }

    @Synchronized
    fun find(predicate: (T)->Boolean) : T?{
        return items.find(predicate)
    }

    @Synchronized
    fun clear(){
        items.clear()
    }
}

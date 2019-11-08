package velvet.multithreading

import java.util.*

open class SynchronizedQueue<T> {

    private val items: LinkedList<T> = LinkedList()

    @Synchronized
    fun pop(): T? {
        if (items.isEmpty()) { return null; }
        return items.pop()
    }

    @Synchronized
    fun add(item: T){
        items.add(item)
    }

    @Synchronized
    fun addFront(item: T){
        items.addFirst(item)
    }

    @Synchronized
    fun isEmpty(): Boolean{
        return items.isEmpty()
    }

    @Synchronized
    fun toList(): List<T>{
        return items.toList()
    }
}
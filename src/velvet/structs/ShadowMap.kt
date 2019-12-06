package velvet.structs

class ShadowMap<T,U>(private val watchListGetter: ()->Collection<T>,
                     private val factory: (T)->U) {

    private val items: MutableMap<T,U> = HashMap()

    init{
        update()
    }

    val values: Collection<U>
        get() = items.values
    operator fun get(t: T) = items[t]

    fun update(){
        val watchList = watchListGetter()
        //delete in items but no longer in watch set
        (items.keys - watchList).forEach { items.remove(it) }
        //add from watch list not yet in items
        watchList.filter { it !in items.keys }.forEach { items[it] = factory(it) }
    }
}
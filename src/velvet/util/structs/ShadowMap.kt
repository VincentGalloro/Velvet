package velvet.util.structs

class ShadowMap<T,U>(private val watchList: Collection<T>,
                     private val factory: (T)->U) {

    private val items: MutableMap<T,U> = mutableMapOf()

    init{
        update()
    }

    val values: Collection<U>
        get() = items.values
    operator fun get(t: T) = items[t]

    fun update(){
        //delete in items but no longer in watch set
        items -= (items.keys - watchList)
        //add from watch list not yet in items
        (watchList - items.keys).forEach { items[it] = factory(it) }
    }
}
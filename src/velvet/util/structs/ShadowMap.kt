package velvet.util.structs

/*
Maintains a second list of items which mirrors the original list.
The elements in the shadow-map share a one to one relationship with elements of the original list
Shadow map must be updated to reflect the changes in the original list
 */
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
        items -= (items.keys - watchList)
        //add from watch list not yet in items
        (watchList - items.keys).forEach { items[it] = factory(it) }
    }
}
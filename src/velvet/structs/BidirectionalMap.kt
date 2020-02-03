package velvet.structs

open class BidirectionalMap<T, U> {

    private val tuMap: MutableMap<T, MutableSet<U>> = HashMap()
    private val utMap: MutableMap<U, MutableSet<T>> = HashMap()

    @JvmName("getByFirst")
    operator fun get(t: T): Set<U> = tuMap[t] ?: setOf()
    @JvmName("getBySecond")
    operator fun get(u: U): Set<T> = utMap[u] ?: setOf()

    fun add(t: T, u: U) {
        tuMap.getOrPut(t, { mutableSetOf() }).add(u)
        utMap.getOrPut(u, { mutableSetOf() }).add(t)
    }

    fun remove(t: T, u: U) {
        tuMap[t]?.remove(u)
        utMap[u]?.remove(t)

        if (tuMap[t]?.isEmpty() == true) tuMap.remove(t)
        if (utMap[u]?.isEmpty() == true) utMap.remove(u)
    }
}
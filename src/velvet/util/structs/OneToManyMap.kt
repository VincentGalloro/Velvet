package velvet.util.structs

open class OneToManyMap<T, U> {

    private val tuMap: MutableMap<T, MutableSet<U>> = HashMap()
    private val utMap: MutableMap<U, T> = HashMap()

    fun getPairs() = utMap.map { it.value to it.key }

    @JvmName("getByFirst")
    operator fun get(t: T): Set<U> = tuMap[t] ?: setOf()
    @JvmName("getBySecond")
    operator fun get(u: U): T? = utMap[u]

    operator fun set(u: U, t: T) {
        remove(u)

        tuMap.getOrPut(t, { mutableSetOf() }).add(u)
        utMap[u] = t
    }

    @JvmName("removeByFirst")
    fun remove(u: U) {
        utMap[u]?.let { t ->
            tuMap[t]?.remove(u)
            if (tuMap[t]?.isEmpty() == true) tuMap.remove(t)
        }
        utMap.remove(u)
    }

    @JvmName("removeBySecond")
    fun remove(t: T) = tuMap[t]?.toList()?.forEach { u -> remove(u) }
}
package velvet.structs

interface ManyToManyMap<T, U> {

    fun getPairs(): List<Pair<T, U>>

    @JvmName("getByFirst") @Suppress("INAPPLICABLE_JVM_NAME")
    operator fun get(t: T): Set<U>
    @JvmName("getBySecond") @Suppress("INAPPLICABLE_JVM_NAME")
    operator fun get(u: U): Set<T>

    fun add(t: T, u: U)
    fun remove(t: T, u: U)

    @JvmName("removeByFirst") @Suppress("INAPPLICABLE_JVM_NAME")
    fun remove(t: T)
    @JvmName("removeBySecond") @Suppress("INAPPLICABLE_JVM_NAME")
    fun remove(u: U)
}

class ManyToManyMapImpl<T, U> : ManyToManyMap<T, U>{

    private val tuMap: MutableMap<T, MutableSet<U>> = HashMap()
    private val utMap: MutableMap<U, MutableSet<T>> = HashMap()

    override fun getPairs() = tuMap.flatMap { e -> e.value.map { e.key to it } }

    @JvmName("removeByFirst") @Suppress("INAPPLICABLE_JVM_NAME")
    override operator fun get(t: T): Set<U> = tuMap[t] ?: setOf()
    @JvmName("removeBySecond") @Suppress("INAPPLICABLE_JVM_NAME")
    override operator fun get(u: U): Set<T> = utMap[u] ?: setOf()

    override fun add(t: T, u: U) {
        tuMap.getOrPut(t, ::mutableSetOf).add(u)
        utMap.getOrPut(u, ::mutableSetOf).add(t)
    }

    override fun remove(t: T, u: U) {
        tuMap[t]?.remove(u)
        utMap[u]?.remove(t)

        if (tuMap[t]?.isEmpty() == true) tuMap.remove(t)
        if (utMap[u]?.isEmpty() == true) utMap.remove(u)
    }

    @JvmName("removeByFirst") @Suppress("INAPPLICABLE_JVM_NAME")
    override fun remove(t: T){ tuMap[t]?.toList()?.forEach { remove(t, it) } }
    @JvmName("removeBySecond") @Suppress("INAPPLICABLE_JVM_NAME")
    override fun remove(u: U){ utMap[u]?.toList()?.forEach { remove(it, u) } }
}
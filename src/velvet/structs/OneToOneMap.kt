package velvet.structs

class OneToOneMap<T, U> {

    private val tuMap: MutableMap<T, U> = HashMap()
    private val utMap: MutableMap<U, T> = HashMap()

    fun getPairs() = tuMap.map { it.key to it.value }

    @JvmName("getByFirst")
    operator fun get(t: T): U? = tuMap[t]
    @JvmName("getBySecond")
    operator fun get(u: U): T? = utMap[u]

    fun add(t: T, u: U) = set(t, u)

    @JvmName("setByFirst")
    operator fun set(t: T, u: U) {
        remove(t)
        remove(u)
        tuMap[t] = u
        utMap[u] = t
    }
    @JvmName("setBySecond")
    operator fun set(u: U, t: T) = set(t, u)

    @JvmName("removeByFirst")
    fun remove(t: T){
        tuMap[t]?.let(utMap::remove)
        tuMap.remove(t)
    }
    @JvmName("removeBySecond")
    fun remove(u: U){
        utMap[u]?.let(tuMap::remove)
        utMap.remove(u)
    }
}
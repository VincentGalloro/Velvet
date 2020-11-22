package velvet.util.structs

import java.util.*
import java.util.function.Function
import java.util.stream.Stream
import kotlin.random.Random

class AutoSet<K, V>(private val keyExtractor: (V)->K) {

    private val map: MutableMap<K, V> = mutableMapOf()

    val size: Int
        get() = map.size
    val values: Sequence<V>
        get() = map.asSequence().map { it.value }

    fun add(value: V) {
        val key = keyExtractor(value)
        if (key in map) {
            return
        }
        map[key] = value
    }

    operator fun get(key: K): V? = map[key]

    operator fun contains(value: V) = keyExtractor(value) in map

    fun clear() {
        map.clear()
    }

    fun remove(value: V) {
        map.remove(keyExtractor(value))
    }
}
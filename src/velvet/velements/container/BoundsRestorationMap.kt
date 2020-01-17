package velvet.velements.container

import velvet.structs.Bounds

private class Item<V : VContainer, K>(private val vSupplier: ()->Collection<V>,
                                      private val keyExtractor: (V)->K?){

    fun addToBounds(bounds: MutableMap<K, Bounds>){
        vSupplier().forEach { v -> keyExtractor(v)?.let { bounds[it] = v.bounds } }
    }

    fun restore(bounds: MutableMap<K, Bounds>){
        vSupplier().forEach { v -> bounds[keyExtractor(v)]?.let { v.bounds = it } }
    }
}

class BoundsRestorationMap<K> {

    private val bounds: MutableMap<K, Bounds> = HashMap()
    private val containerMaps: MutableList<Item<*, K>> = ArrayList()

    fun <V : VContainer> store(vSupplier: ()->V, keyExtractor: (V)->K?){
        storeCollection({ listOf(vSupplier()) }, keyExtractor)
    }
    fun <V : VContainer> storeCollection(vSupplier: ()->Collection<V>, keyExtractor: (V)->K?){
        containerMaps.add(Item(vSupplier, keyExtractor).also { it.addToBounds(bounds) })
    }

    fun restore(){
        containerMaps.forEach { it.restore(bounds) }
    }
}
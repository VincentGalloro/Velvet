package velvet.ui

import velvet.structs.Bounds
import velvet.ui.vcontainer.VContainer

private class Item<K>(private val vSupplier: ()->Collection<VContainer>,
                      private val keyExtractor: (VContainer)->K?){

    fun addToBounds(bounds: MutableMap<K, Bounds>){
        vSupplier().forEach { v -> keyExtractor(v)?.let { bounds[it] = v.bounds } }
    }

    fun restore(bounds: MutableMap<K, Bounds>){
        vSupplier().forEach { v -> bounds[keyExtractor(v)]?.let { v.bounds = it } }
    }
}

class BoundsRestorationMap<K> {

    private val bounds: MutableMap<K, Bounds> = HashMap()
    private val containerMaps: MutableList<Item<K>> = ArrayList()

    fun store(vSupplier: ()-> VContainer, keyExtractor: (VContainer)->K?){
        storeCollection({ listOf(vSupplier()) }, keyExtractor)
    }
    fun storeCollection(vSupplier: ()->Collection<VContainer>, keyExtractor: (VContainer)->K?){
        containerMaps.add(Item(vSupplier, keyExtractor).also { it.addToBounds(bounds) })
    }

    fun restore(){
        containerMaps.forEach { it.restore(bounds) }
    }
}
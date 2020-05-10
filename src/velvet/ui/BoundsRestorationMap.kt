package velvet.ui

import velvet.structs.Bounds
import velvet.ui.vcontainer.VContainer

private class Item<T, K>(private val listSupplier: ()->Collection<T>,
                         private val keyExtractor: (T)->K,
                         private val uiNodeExtractor: (T)->UINode){

    fun addToBounds(bounds: MutableMap<K, Bounds>){
        listSupplier().forEach { bounds[keyExtractor(it)] = uiNodeExtractor(it).bounds }
    }

    fun restore(bounds: MutableMap<K, Bounds>){
        listSupplier().forEach { t -> bounds[keyExtractor(t)]?.let { uiNodeExtractor(t).bounds = it } }
    }
}

class BoundsRestorationMap<K> {

    private val bounds: MutableMap<K, Bounds> = HashMap()
    private val items: MutableList<Item<*, K>> = ArrayList()

    fun <T> storeCollection(listSupplier: ()->Collection<T>,
                            keyExtractor: (T)->K,
                            uiNodeExtractor: (T)->UINode){
        items.add(Item(listSupplier, keyExtractor, uiNodeExtractor).also {
            it.addToBounds(bounds)
        })
    }

    fun restore(){
        items.forEach { it.restore(bounds) }
    }
}
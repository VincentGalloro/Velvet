package velvet.ui

import velvet.util.types.spatial.Bounds

private class Item<T : UINode, K>(private val listSupplier: ()->Collection<T>,
                                  private val keyExtractor: (T)->K){

    fun addToBounds(bounds: MutableMap<K, Bounds>){
        listSupplier().forEach { bounds[keyExtractor(it)] = it.bounds }
    }

    fun restore(bounds: MutableMap<K, Bounds>){
        listSupplier().forEach { t -> bounds[keyExtractor(t)]?.let { t.bounds = it } }
    }
}

class BoundsRestorationMap<K> {

    private val bounds: MutableMap<K, Bounds> = HashMap()
    private val items: MutableList<Item<*, K>> = ArrayList()

    fun <T : UINode> storeCollection(listSupplier: ()->Collection<T>,
                        keyExtractor: (T)->K){
        items.add(Item(listSupplier, keyExtractor).also {
            it.addToBounds(bounds)
        })
    }

    fun restore(){
        items.forEach { it.restore(bounds) }
    }
}
package velvet.velements.container

import velvet.structs.Bounds

private class SingleItem<V : VContainer, K>(val vSupplier: ()->V,
                                            val keyExtractor: (V)->K?){

    fun addToBounds(bounds: MutableMap<K, Bounds>){
        vSupplier().also { v -> keyExtractor(v)?.let { bounds[it] = v.bounds } }
    }

    fun restore(bounds: MutableMap<K, Bounds>){
        vSupplier().also { v -> bounds[keyExtractor(v)]?.let { v.bounds = it } }
    }
}

private class CollectionItem<V : VContainer, K>(val vSupplier: ()->Collection<V>,
                                                val keyExtractor: (V)->K?){

    fun addToBounds(bounds: MutableMap<K, Bounds>){
        vSupplier().forEach { v -> keyExtractor(v)?.let { bounds[it] = v.bounds } }
    }

    fun restore(bounds: MutableMap<K, Bounds>){
        vSupplier().forEach { v -> bounds[keyExtractor(v)]?.let { v.bounds = it } }
    }
}

class BoundsRestorationMap<K> {

    private val bounds: MutableMap<K, Bounds> = HashMap()
    private val singleContainerMaps: MutableList<SingleItem<out VContainer, K>> = ArrayList()
    private val collectionContainerMaps: MutableList<CollectionItem<out VContainer, K>> = ArrayList()

    fun <V : VContainer> store(vSupplier: ()->V, keyExtractor: (V)->K?){
        singleContainerMaps.add(SingleItem(vSupplier, keyExtractor)
                .also { it.addToBounds(bounds) })
    }
    fun <V : VContainer> storeCollection(vSupplier: ()->Collection<V>, keyExtractor: (V)->K?){
        collectionContainerMaps.add(CollectionItem(vSupplier, keyExtractor)
                .also { it.addToBounds(bounds) })
    }

    fun restore(){
        singleContainerMaps.forEach { it.restore(bounds) }
        collectionContainerMaps.forEach { it.restore(bounds) }
    }
}
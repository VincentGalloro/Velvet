package velvet.multithreading

data class KeyValueNode<K,V>(val key: K,
                             val value: V,
                             var prevNode: KeyValueNode<K,V>? = null,
                             var nextNode: KeyValueNode<K,V>? = null)
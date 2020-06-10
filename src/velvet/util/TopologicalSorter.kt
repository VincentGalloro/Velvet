package velvet.util

import velvet.util.structs.Hierarchy

class TopologicalSorter {

    fun <T : Any> sort(items: Collection<T>, hierarchy: Hierarchy<T>): List<List<T>>{
        val open = items.toMutableSet()
        val closed: MutableSet<T> = mutableSetOf()
        val out: MutableList<List<T>> = mutableListOf()
        while(open.isNotEmpty()){
            val layer = open.filter { hierarchy.getParents(it).none { it !in closed } }
            if(layer.isEmpty()){
                throw Exception("Cannot preform sort, hierarchy contains cycles")
            }
            out.add(layer)
            open -= layer
            closed += layer
        }
        return out
    }
}
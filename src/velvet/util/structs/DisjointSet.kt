package velvet.util.structs

class DisjointSet(size: Int) {

    private val parents: MutableList<Int> = MutableList(size){ it }
    private val ranks: MutableList<Int> = MutableList(size){ 0 }

    private fun find(a: Int): Int {
        if (parents[a] != a) {
            parents[a] = find(parents[a])
        }
        return parents[a]
    }

    fun sameSet(a: Int, b: Int): Boolean {
        return find(a) == find(b)
    }

    fun union(a: Int, b: Int): Boolean {
        var ra = find(a)
        var rb = find(b)
        if (ra == rb) {
            return false
        }
        if (ranks[rb] > ranks[ra]) {
            val temp = ra
            ra = rb
            rb = temp
        }
        parents[rb] = ra
        if (ranks[ra] == ranks[rb]) {
            ranks[ra]++
        }
        return true
    }
}
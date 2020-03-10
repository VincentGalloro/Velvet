package velvet.io.impl

import velvet.io.Loader
import velvet.io.Reader
import velvet.io.Writer

class ListIO private constructor(){
    companion object{

        fun <T> createLoader(itemReader: Reader<T>) =
                Loader.basic<MutableList<T>>("1.0") { d, t ->
                    val size = d.readInt()
                    repeat(size) { t.add(itemReader.read(d)) }
                }

        fun <T> createReader(itemReader: Reader<T>) =
                Reader.basic("1.0") { d ->
                    val size = d.readInt()
                    List(size) { itemReader.read(d) }
                }

        fun <T> createWriter(itemWriter: Writer<T>) =
                Writer<List<T>>("1.0") { d, t ->
                    d.writeInt(t.size)
                    t.forEach { itemWriter.write(d, it) }
                }

        fun createIDPairReader() = createReader(Reader.basic("1.0") { it.readInt() to it.readInt() })
        fun createIDPairWriter() = createWriter(Writer<Pair<Int, Int>>("1.0") { d, t ->
            d.writeInt(t.first)
            d.writeInt(t.second)
        })
    }
}

fun <T> List<T>.toItemIDMap(): Map<T, Int> = mapIndexed{ index, t -> t to index }.toMap()
fun <T> List<T>.toIDItemMap(): Map<Int, T> = mapIndexed{ index, t -> index to t }.toMap()

fun <T, U> List<Pair<T, U>>.resolveToIDs(tMap: Map<T, Int>, uMap: Map<U, Int>): List<Pair<Int, Int>> =
        map { (tMap[it.first] ?: -1) to (uMap[it.second] ?: -1) }
fun <T, U> List<Pair<Int, Int>>.resolveFromIDs(tMap: Map<Int, T>, uMap: Map<Int, U>): List<Pair<T, U>> =
        mapNotNull { tMap[it.first]?.let { t -> uMap[it.second]?.let { u -> t to u } } }
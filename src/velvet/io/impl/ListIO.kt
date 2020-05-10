package velvet.io.impl

import velvet.io.Loader
import velvet.io.Reader
import velvet.io.Writer
import velvet.structs.OneToOneMap
import java.io.DataInputStream
import java.io.DataOutputStream

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
                Writer<Collection<T>>("1.0") { d, t ->
                    d.writeInt(t.size)
                    t.forEach { itemWriter.write(d, it) }
                }

        fun <T> createIDReader(itemReader: Reader<T>, map: OneToOneMap<T, Int>) =
                Reader.basic("1.0") { d ->
                    val size = d.readInt()
                    List(size) {
                        val id = d.readInt()
                        val item = itemReader.read(d)
                        map[item] = id
                        item
                    }
                }
        fun <T> createIDWriter(itemWriter: Writer<T>, map: OneToOneMap<T, Int>) =
                Writer<Collection<T>>("1.0") { d, t ->
                    d.writeInt(t.size)
                    t.forEach {
                        d.writeInt(map[it] ?: -1)
                        itemWriter.write(d, it)
                    }
                }

        fun <T, U> createIDPairReader(tMap: OneToOneMap<T, Int>,
                                      uMap: OneToOneMap<U, Int>,
                                      contentReader: (DataInputStream, T, U)->Unit = {_,_,_->}) =
                Reader.basic<List<Pair<T, U>>>("1.0") { d ->
                    val size = d.readInt()
                    List(size) {
                        tMap[d.readInt()]?.let { t ->
                            uMap[d.readInt()]?.let { u ->
                                contentReader(d, t, u)
                                (t to u)
                            }
                        }
                    }.filterNotNull()
                }
        fun <T, U> createIDPairWriter(tMap: OneToOneMap<T, Int>,
                                      uMap: OneToOneMap<U, Int>,
                                      contentWriter: (DataOutputStream, T, U)->Unit = {_,_,_->}) =
                Writer<List<Pair<T, U>>>("1.0") { d, t ->
                    d.writeInt(t.size)
                    t.forEach {
                        d.writeInt(tMap[it.first] ?: -1)
                        d.writeInt(uMap[it.second] ?: -1)
                        contentWriter(d, it.first, it.second)
                    }
                }
    }
}
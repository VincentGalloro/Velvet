package velvet.io

import java.io.DataInputStream

abstract class IDListReader<T>(private val tReader: Reader<T>) : VersionedReader<Map<Int, T>> {

    override fun read(dataInputStream: DataInputStream): Map<Int, T> {
        val itemsByID: MutableMap<Int, T> = mutableMapOf()
        repeat(dataInputStream.readInt()){
            val id = dataInputStream.readInt()
            itemsByID[id] = tReader.read(dataInputStream)
        }
        return itemsByID
    }
}
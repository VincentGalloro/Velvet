package velvet.io.impl

import velvet.io.*

class ListIO private constructor(){
    companion object{

        fun <T> createLoader(itemReader: Reader<T>) = Loader<MutableList<T>>(listOf(VersionedLoader { dataInputStream, t ->
            val size = dataInputStream.readInt()
            repeat(size) { t.add(itemReader.read(dataInputStream)) }
        }))

        fun <T> createReader(itemReader: Reader<T>) = Reader(listOf(VersionedReader{ dataInputStream ->
            val size = dataInputStream.readInt()
            List(size) { itemReader.read(dataInputStream) }
        }))

        fun <T> createWriter(itemWriter: Writer<T>) = Writer<List<T>> { dataOutputStream, t ->
            dataOutputStream.writeInt(t.size)
            t.forEach { itemWriter.write(dataOutputStream, it) }
        }
    }
}
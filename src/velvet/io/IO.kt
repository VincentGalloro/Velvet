package velvet.io

import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Exception

class VersionedLoader<T>(val version: String = "",
                         private val loader: (DataInputStream, T)->Unit){
    fun load(dataInputStream: DataInputStream, t: T) = loader(dataInputStream, t)
}
class VersionedReader<T>(val version: String = "",
                         private val reader: (DataInputStream)->T){
    fun read(dataInputStream: DataInputStream) = reader(dataInputStream)
}

class Writer<T>(private val version: String = "",
                private val writer: (DataOutputStream, T)->Unit){
    fun write(dataOutputStream: DataOutputStream, t: T){
        dataOutputStream.writeUTF(version)
        writer(dataOutputStream, t)
    }
}

class Loader<T>(private val versionedLoaders: List<VersionedLoader<T>>){

    companion object{
        fun <T> basic(version: String = "", loader: (DataInputStream, T)->Unit) =
                Loader(listOf(VersionedLoader(version, loader)))
    }

    fun load(dataInputStream: DataInputStream, t: T){
        val version = dataInputStream.readUTF()
        versionedLoaders.firstOrNull { it.version == version }?.let { return it.load(dataInputStream, t) }
        throw Exception("${javaClass.simpleName} can not load object of version $version")
    }
}

class Reader<T>(private val versionedReaders: List<VersionedReader<T>>){

    companion object{
        fun <T> basic(version: String = "", reader: (DataInputStream)->T) =
                Reader(listOf(VersionedReader(version, reader)))
    }

    fun read(dataInputStream: DataInputStream): T{
        val version = dataInputStream.readUTF()
        versionedReaders.firstOrNull { it.version == version }?.let { return it.read(dataInputStream) }
        throw Exception("${javaClass.simpleName} can not read object of version $version")
    }
}
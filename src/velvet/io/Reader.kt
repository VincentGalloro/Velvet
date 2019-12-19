package velvet.io

import java.io.DataInputStream

abstract class Reader<T>{

    abstract val versionedReaders: List<VersionedReader<T>>

    fun read(dataInputStream: DataInputStream): T{
        val version = dataInputStream.readUTF()
        versionedReaders.firstOrNull { it.version == version }?.let {
            return it.read(dataInputStream)
        }
        throw NoMatchingVersionHandlerException("${javaClass.simpleName} can not read object of version $version")
    }
}
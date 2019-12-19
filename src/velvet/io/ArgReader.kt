package velvet.io

import java.io.DataInputStream

abstract class ArgReader<T, R> {

    abstract val versionedReaders: List<VersionedArgReader<T, R>>

    fun read(dataInputStream: DataInputStream, t: T): R {
        val version = dataInputStream.readUTF()
        versionedReaders.firstOrNull { it.version == version }?.let {
            return it.read(dataInputStream, t)
        }
        throw NoMatchingVersionHandlerException("${javaClass.simpleName} can not read object of version $version")
    }
}
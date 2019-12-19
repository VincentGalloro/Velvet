package velvet.io

import java.io.DataInputStream

abstract class Loader<T>{

    abstract val versionedLoaders: List<VersionedLoader<T>>

    fun load(dataInputStream: DataInputStream, t: T){
        val version = dataInputStream.readUTF()
        versionedLoaders.firstOrNull { it.version == version }?.let {
            return it.load(dataInputStream, t)
        }
        throw NoMatchingVersionHandlerException("${javaClass.simpleName} can not load object of version $version")
    }
}
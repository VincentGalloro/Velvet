package velvet.io

import java.io.DataInputStream

interface VersionedLoader<T> {

    val version: String

    fun load(dataInputStream: DataInputStream, t: T)
}
package velvet.io

import java.io.DataInputStream

interface VersionedReader<T> {

    val version: String

    fun read(dataInputStream: DataInputStream): T
}
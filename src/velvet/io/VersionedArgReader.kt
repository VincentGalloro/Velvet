package velvet.io

import java.io.DataInputStream

interface VersionedArgReader<T, R> {

    val version: String

    fun read(dataInputStream: DataInputStream, t: T): R
}
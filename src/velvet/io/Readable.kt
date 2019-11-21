package velvet.io

import java.io.DataInputStream
import java.io.IOException

interface Readable<T> {

    @Throws(IOException::class)
    fun read(dataInputStream: DataInputStream): T
}
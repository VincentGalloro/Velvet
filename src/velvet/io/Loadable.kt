package velvet.io

import java.io.DataInputStream
import java.io.IOException

interface Loadable {

    @Throws(IOException::class)
    fun load(dataInputStream: DataInputStream)
}
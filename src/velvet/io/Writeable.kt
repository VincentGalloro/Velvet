package velvet.io

import java.io.DataOutputStream
import java.io.IOException

interface Writeable {

    @Throws(IOException::class)
    fun write(dataOutputStream: DataOutputStream)
}
package velvet.io

import java.io.DataOutputStream

abstract class Writer<T, R> {

    abstract val version: String

    fun write(dataOutputStream: DataOutputStream, t: T): R{
        dataOutputStream.writeUTF(version)
        return dataWrite(dataOutputStream, t)
    }

    protected abstract fun dataWrite(dataOutputStream: DataOutputStream, t: T): R
}
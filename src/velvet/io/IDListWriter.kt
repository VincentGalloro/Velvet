package velvet.io

import java.io.DataOutputStream

abstract class IDListWriter<T>(private val tWriter: Writer<T, *>)
    : Writer<List<T>, Map<T, Int>>() {

    override fun dataWrite(dataOutputStream: DataOutputStream, t: List<T>): Map<T, Int> {
        val itemIDs: MutableMap<T, Int> = mutableMapOf()
        dataOutputStream.writeInt(t.size)
        t.forEachIndexed { index, item ->
            dataOutputStream.writeInt(index)
            itemIDs[item] = index
            tWriter.write(dataOutputStream, item)
        }
        return itemIDs
    }
}
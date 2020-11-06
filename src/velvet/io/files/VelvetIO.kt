package velvet.io.files

import java.io.DataInputStream
import java.io.DataOutputStream

interface VelvetWriteable{
    fun write(dOut: DataOutputStream)
}

interface VelvetLoadable{
    fun load(dIn: DataInputStream)
}

class VelvetIO {
    companion object{
        fun createAutoBackupStore(name: String) = AutoBackupBridge.basicSetup(name)

        fun <T> readNullable(dIn: DataInputStream, reader: ()->T): T?{
            if(!dIn.readBoolean()) return null
            return reader()
        }

        fun <T> writeNullable(item: T?, dOut: DataOutputStream, writer: (T)->Unit){
            dOut.writeBoolean(item != null)
            item?.let(writer)
        }

        fun <T> loadCollection(collection: MutableCollection<T>,
                               dIn: DataInputStream,
                               itemReader: ()->T){
            collection.clear()
            repeat(dIn.readInt()){
                collection.add(itemReader())
            }
        }

        fun <T, U> loadMap(map: MutableMap<T, U>,
                           dIn: DataInputStream,
                           keyReader: ()->T,
                           valueReader: ()->U){
            map.clear()
            repeat(dIn.readInt()){
                map[keyReader()] = valueReader()
            }
        }

        fun <T> loadIdMap(map: MutableMap<Int, T>,
                          dIn: DataInputStream,
                          valueReader: ()->T){
            loadMap(map, dIn, { dIn.readInt() }, valueReader)
        }

        fun <T> writeCollection(collection: Collection<T>,
                                dOut: DataOutputStream,
                                itemWriter: (T)->Unit){
            dOut.writeInt(collection.size)
            collection.forEach {
                itemWriter(it)
            }
        }

        @JvmName("writeWriteableCollection")
        fun <T : VelvetWriteable> writeCollection(collection: Collection<T>,
                                                  dOut: DataOutputStream){
            writeCollection(collection, dOut) { it.write(dOut) }
        }

        fun <T, U> writeMap(map: Map<T, U>,
                            dOut: DataOutputStream,
                            keyWriter: (T)->Unit,
                            valueWriter: (U)->Unit){
            dOut.writeInt(map.size)
            map.forEach { (t, u) ->
                keyWriter(t)
                valueWriter(u)
            }
        }

        @JvmName("writeWriteableValueMap")
        fun <T, U : VelvetWriteable> writeMap(map: Map<T, U>,
                                              dOut: DataOutputStream,
                                              keyWriter: (T)->Unit){
            writeMap(map, dOut, keyWriter, { it.write(dOut) })
        }

        fun <T> writeIdMap(map: Map<Int, T>, dOut: DataOutputStream, valueWriter: (T)->Unit){
            writeMap(map, dOut, { dOut.writeInt(it) }, valueWriter)
        }

        @JvmName("writeWriteableValueIdMap")
        fun <T : VelvetWriteable> writeIdMap(map: Map<Int, T>,
                                             dOut: DataOutputStream){
            writeMap(map, dOut, { dOut.writeInt(it) }, { it.write(dOut) })
        }
    }
}
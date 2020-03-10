package velvet.io.files

import velvet.io.FailedToValidateException
import velvet.io.Loader
import velvet.io.Reader
import velvet.io.Writer

class ValidatingFileBridge(private val fileBridge: FileBridge): FileBridge by fileBridge{

    companion object {
        private val validationString = "[Velvet-1.0-Validated]"

        private fun <T> createValidatingWriter(writer: Writer<T>) =
                Writer<T>("1.0") { dataOutputStream, t ->
                    writer.write(dataOutputStream, t)
                    dataOutputStream.writeUTF(validationString)
                }
        private fun <T> createValidatingReader(reader: Reader<T>) =
                Reader.basic("1.0") { dataInputStream ->
                    val out = reader.read(dataInputStream)
                    if(dataInputStream.readUTF() != validationString) throw FailedToValidateException()
                    out
                }
        private fun <T> createValidatingLoader(loader: Loader<T>) =
                Loader.basic<T>("1.0") { dataInputStream, t ->
                    loader.load(dataInputStream, t)
                    if(dataInputStream.readUTF() != validationString) throw FailedToValidateException()
                }
    }

    override fun <T> writeToFile(writer: Writer<T>, t: T) = fileBridge.writeToFile(createValidatingWriter(writer), t)
    override fun <T> readFromFile(reader: Reader<T>) = fileBridge.readFromFile(createValidatingReader(reader))
    override fun <T> loadFromFile(loader: Loader<T>, t: T) = fileBridge.loadFromFile(createValidatingLoader(loader), t)
}
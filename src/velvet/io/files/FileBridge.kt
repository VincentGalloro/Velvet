package velvet.io.files

import velvet.io.Loader
import velvet.io.Reader
import velvet.io.Writer
import java.nio.file.Path

interface FileBridge {

    fun <T> writeToFile(writer: Writer<T>, t: T)
    fun <T> readFromFile(reader: Reader<T>): T
    fun <T> loadFromFile(loader: Loader<T>, t : T): Boolean

    fun copyTo(target: Path)
    fun copyFrom(source: Path)
}
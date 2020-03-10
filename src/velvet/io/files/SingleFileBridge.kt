package velvet.io.files

import velvet.io.Loader
import velvet.io.Reader
import velvet.io.Writer
import java.io.DataInputStream
import java.io.DataOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class SingleFileBridge(private val file: Path) : FileBridge{

    override fun <T> writeToFile(writer: Writer<T>, t: T) {
        Files.createDirectories(file.toAbsolutePath().parent)
        DataOutputStream(Files.newOutputStream(file)).use { writer.write(it, t) }
    }

    override fun <T> readFromFile(reader: Reader<T>): T {
        DataInputStream(Files.newInputStream(file)).use { return reader.read(it) }
    }

    override fun <T> loadFromFile(loader: Loader<T>, t: T) {
        DataInputStream(Files.newInputStream(file)).use { loader.load(it, t) }
    }

    override fun copyTo(target: Path) {
        Files.createDirectories(target.toAbsolutePath().parent)
        Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING)
    }

    override fun copyFrom(source: Path) {
        Files.createDirectories(file.toAbsolutePath().parent)
        Files.copy(source, file, StandardCopyOption.REPLACE_EXISTING)
    }
}
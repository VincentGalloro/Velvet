package velvet.io.files

import java.io.DataInputStream
import java.io.DataOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

class SingleFileBridge(private val file: Path) : FileBridge{

    override fun writeToFile(writeable: VelvetWriteable) {
        Files.createDirectories(file.toAbsolutePath().parent)
        writeable.write(DataOutputStream(Files.newOutputStream(file)))
    }

    override fun loadFromFile(loadable: VelvetLoadable) {
        loadable.load(DataInputStream(Files.newInputStream(file)))
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